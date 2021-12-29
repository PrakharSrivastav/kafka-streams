package org.example.kafka;

import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.example.config.TestConfig;
import org.example.model.avro.Measurement;
import org.example.model.avro.MeasurementAggregate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationFieldType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MeasurementStream.class)
@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@ActiveProfiles("test")
public class MeasurementStreamTest {
    private static final String SCHEMA_REGISTRY_SCOPE = MeasurementStreamTest.class.getName();
    private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;

    @Autowired
    NewTopic topic1;

    @Autowired
    NewTopic topic2;

    private MeasurementStream ms;
    private TestInputTopic<String, Measurement> inputTopic;
    private TestOutputTopic<String, MeasurementAggregate> outputTopic;
    private List<Measurement> list = new ArrayList<>();
    private TopologyTestDriver testDriver;

    @BeforeEach
    void setUp() {

        var serdeConfig = Map.of("schema.registry.url", MOCK_SCHEMA_REGISTRY_URL);

        ms = new MeasurementStream(topic1, topic2, serdeConfig);
        var rand = new Random();
        var tt = new DateTime().minusDays(1).withZone(DateTimeZone.UTC);
        DateTime dt = null;

        int count = 0;
        do {
            dt = Objects.requireNonNullElse(dt, tt).withFieldAdded(DurationFieldType.millis(), 20);
            list.add(Measurement.newBuilder()
                .setDevice("A").setValue(rand.nextDouble(49.9, 50.1)).setType("Hz").setTimestamp(dt.toString())
                .build());
            list.add(Measurement.newBuilder()
                .setDevice("B").setValue(rand.nextDouble(49.9, 50.1)).setType("Hz").setTimestamp(dt.toString())
                .build());
            list.add(Measurement.newBuilder()
                .setDevice("C").setValue(rand.nextDouble(49.9, 50.1)).setType("Hz").setTimestamp(dt.toString())
                .build());

            count++;
        } while (count < 10);


        StreamsBuilder streamsBuilder = new StreamsBuilder();
        ms.buildPipeline(streamsBuilder);
        Topology topology = streamsBuilder.build();
        System.out.println(topology.describe().toString());

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 5);
        props.put("schema.registry.url", MOCK_SCHEMA_REGISTRY_URL);

        var serde_string = Serdes.String();
        var serde_measurement = new SpecificAvroSerde<Measurement>();
        var serde_measurement_agg = new SpecificAvroSerde<MeasurementAggregate>();

        // Configure Serdes to use the same mock schema registry URL
        serde_measurement.configure(serdeConfig, false);
        serde_measurement_agg.configure(serdeConfig, false);

        testDriver = new TopologyTestDriver(topology, props);
        inputTopic = testDriver.createInputTopic(topic1.name(), serde_string.serializer(),
            serde_measurement.serializer());
        outputTopic = testDriver.createOutputTopic(topic2.name(), serde_string.deserializer(),
            serde_measurement_agg.deserializer());

    }

    @AfterEach
    void afterEach() {
        MockSchemaRegistry.dropScope(SCHEMA_REGISTRY_SCOPE);
        testDriver.close();
    }

    private static final ZoneId zone = ZoneOffset.UTC;

    @Test
    void buildPipeline() {

        Collections.shuffle(list);
        Instant instant = ZonedDateTime.of(2021, 1, 1, 16, 29, 0, 0, zone).toInstant();
        for (int i = 0; i < list.size(); i++) {
            var el = list.get(i);
            if (i % 3 == 0) {
                instant = instant.plusMillis(30);
            }
            inputTopic.pipeInput(el.getTimestamp().toString(), el, instant);
        }
        inputTopic.pipeInput(list.get(0).getTimestamp().toString(), list.get(0), ZonedDateTime.now().toInstant());

        var measurementAggregates = outputTopic.readValuesToList();

        assertNotNull(measurementAggregates);
        assertEquals(10, measurementAggregates.size());
        measurementAggregates.forEach(el -> {
            assertNotNull(el.getDeviceA());
            assertNotNull(el.getDeviceB());
            assertNotNull(el.getDeviceC());
            assertTrue(el.getTimestamp().length() > 0);
        });
    }
}