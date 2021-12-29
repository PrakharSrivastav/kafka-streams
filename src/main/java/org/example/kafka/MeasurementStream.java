package org.example.kafka;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.example.model.avro.CountAndSum;
import org.example.model.avro.DeviceInfo;
import org.example.model.avro.Measurement;
import org.example.model.avro.MeasurementAggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MeasurementStream {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    private final NewTopic topic1;
    private final NewTopic topic2;
    private final Map<String, String> serdeConfig;

    @Autowired
    public MeasurementStream(NewTopic topic1, NewTopic topic2,
                             @Qualifier("serdeConfig") Map<String, String> serdeConfig) {
        this.topic1 = topic1;
        this.topic2 = topic2;
        this.serdeConfig = serdeConfig;
    }
    /*@Autowired
    private NewTopic deviceA;
    @Autowired
    private NewTopic deviceB;
    @Autowired
    private NewTopic deviceC;
    @Autowired
    private NewTopic avgTopic;*/


    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {

        final Serde<Measurement> mmSerde = new SpecificAvroSerde<>();
        mmSerde.configure(serdeConfig, false);

        final Serde<MeasurementAggregate> aggSerde = new SpecificAvroSerde<>();
        aggSerde.configure(serdeConfig, false);


        AtomicInteger count = new AtomicInteger();
        streamsBuilder
            .stream(topic1.name(), Consumed.with(STRING_SERDE, mmSerde))
            .map((k, v) -> new KeyValue<>(v.getTimestamp().toString(), v))
            .groupByKey(Grouped.with(Serdes.String(), mmSerde))
            .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofMillis(1000),Duration.ofMillis(10)))
            .aggregate((Initializer<List<Measurement>>) ArrayList::new
                , (key, value, aggregate) -> {
                    aggregate.add(value);
                    return aggregate;
                }
                , Materialized.with(Serdes.String(), Serdes.ListSerde(ArrayList.class, mmSerde))
            )
            .suppress(Suppressed.untilWindowCloses( Suppressed.BufferConfig.maxRecords(100L).withNoBound()))
            .filter((key, value) -> (((List<Measurement>) value).size()) == 3)
            .mapValues((readOnlyKey, value) -> this.aggregateIt((List<Measurement>) value))
            .toStream()
            .peek((key, value) -> System.out.printf("%d %s %n", count.getAndIncrement(), value.toString()))
            .selectKey((key, value) -> key.toString())
            .to(topic2.name());
    }


    @Autowired
    void buildFreqPipeline(StreamsBuilder streamsBuilder) {
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://0.0.0.0:8081");
        final Serde<Measurement> mmSerde = new SpecificAvroSerde<>();
        mmSerde.configure(serdeConfig, false);

        final Serde<MeasurementAggregate> aggSerde = new SpecificAvroSerde<>();
        aggSerde.configure(serdeConfig, false);


        final Serde<CountAndSum> csmSerde = new SpecificAvroSerde<>();
        csmSerde.configure(serdeConfig, false);

        var stream = streamsBuilder.stream(topic2.name(), Consumed.with(STRING_SERDE, aggSerde));


        var avgStream = stream
            .groupByKey()
            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMillis(20)))
            .aggregate(() -> new CountAndSum(0, 0.0)
                , (key, value, aggregate) -> {
                    aggregate.setCount(aggregate.getCount() == 0 ? 1 : aggregate.getCount() + 1);
                    aggregate.setSum(aggregate.getSum() == 0.0 ? value.getDeviceA()
                        .getValue() : aggregate.getSum() + value.getDeviceA().getValue());
                    return aggregate;
                }
                , Materialized.with(STRING_SERDE, csmSerde)
            )
            .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.maxRecords(100).withNoBound()))
            .mapValues((readOnlyKey, value) -> value.getSum() / value.getCount())
            .toStream()
            .selectKey((key, value) -> key.toString());
        //.peek((key, value) -> System.out.printf("avg freq  key: %s : %s %n", key, value.toString()));

        stream.leftJoin(avgStream,
                (ValueJoiner<MeasurementAggregate, Double, MeasurementAggregate>) (agg, avg) -> {
                    if (avg != null)
                        agg.getDeviceA().setAvgFreq(avg);
                    return agg;
                },
                JoinWindows.of(Duration.ofSeconds(3)))
            .peek((key, value) -> System.out.printf("avg freq  key: %s : %s %n", key, value.toString()));
    }


    private MeasurementAggregate aggregateIt(final List<Measurement> mm) {
        var agg = new MeasurementAggregate();

        if (agg.getTimestamp() == null) {
            agg.setTimestamp(mm.get(0).getTimestamp());
        }

        mm.forEach(el -> {
            switch (el.getDevice().toString()) {
                case "A" -> agg.setDeviceA(DeviceInfo.newBuilder()
                    .setDevice(el.getDevice())
                    .setType(el.getType())
                    .setValue(el.getValue())
                    .setAvgFreq(0.0)
                    .build());
                case "B" -> agg.setDeviceB(DeviceInfo.newBuilder()
                    .setDevice(el.getDevice())
                    .setType(el.getType())
                    .setValue(el.getValue())
                    .setAvgFreq(0.0)

                    .build());
                case "C" -> agg.setDeviceC(DeviceInfo.newBuilder()
                    .setDevice(el.getDevice())
                    .setType(el.getType())
                    .setValue(el.getValue())
                    .setAvgFreq(0.0)
                    .build());
            }
        });
        return agg;
    }

}
