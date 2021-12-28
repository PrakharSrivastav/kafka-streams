package org.example.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.config.TestConfig;
import org.example.model.avro.Measurement;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MeasurementProducer.class)
@DirtiesContext
@Testcontainers
@Import(TestConfig.class)
@ActiveProfiles("test")
class MeasurementProducerTest {

    @Autowired
    MeasurementProducer producer;

    @Autowired
    KafkaConsumer<String, Measurement> testKafkaConsumer;

    @Autowired
    NewTopic topic1;

    @Container
    public static DockerComposeContainer environment =
        new DockerComposeContainer(new File("src/test/resources/docker-compose.yaml"))
            .withExposedService("zookeeper_1", 2181)
            .withExposedService("broker_1", 9092)
            .withExposedService("schema-registry_1", 8081);

    @BeforeAll
    public static void setup() {
        Startables.deepStart(Stream.of(environment)).join();
    }


    @Test
    void sendMessage() {
        var rand = new Random();
        var tt = new DateTime().minusDays(1).withZone(DateTimeZone.UTC);
        var list = new ArrayList<Measurement>();

        list.add(Measurement.newBuilder()
            .setDevice("A").setValue(rand.nextDouble(49.9, 50.1)).setType("Hz").setTimestamp(tt.toString())
            .build());
        list.add(Measurement.newBuilder()
            .setDevice("B").setValue(rand.nextDouble(49.9, 50.1)).setType("Hz").setTimestamp(tt.toString())
            .build());
        list.add(Measurement.newBuilder()
            .setDevice("C").setValue(rand.nextDouble(49.9, 50.1)).setType("Hz").setTimestamp(tt.toString())
            .build());

        Collections.shuffle(list);
        list.forEach(m -> this.producer.sendMessage(m.getTimestamp().toString(), m));

        testKafkaConsumer.subscribe(List.of(topic1.name()));

        AtomicInteger count = new AtomicInteger();
        testKafkaConsumer.poll(Duration.ofMillis(10000)).forEach(rec -> {
            count.getAndIncrement();
            assertEquals(tt.toString(), rec.key());
            assertEquals(tt.toString(), rec.value().getTimestamp().toString());
            assertEquals("Hz", rec.value().getType().toString());
            assertTrue(49.9 <= rec.value().getValue() && 50.1 >= rec.value().getValue());
        });
        assertEquals(3, count.get());
    }


}