package org.example;

import org.example.kafka.MeasurementProducer;
import org.example.model.avro.Measurement;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "org.example.config")
public class Application implements CommandLineRunner {

    @Autowired
    MeasurementProducer producer;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void run(String... args) throws Exception {
        var rand = new Random();
        var tt = new DateTime().minusDays(1).withZone(DateTimeZone.UTC);
        DateTime dt = null;
        var list = new ArrayList<Measurement>();

        do {
            var count = 0;
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
                Thread.sleep((long) (Math.random() * 10));
            } while (count < 100);

            Collections.shuffle(list);
            list
                .forEach(m -> this.producer.sendMessage(m.getTimestamp().toString(), m));
            list.clear();
            Thread.sleep(1000 * 60 * 2); // sleep for 2 minutes
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><");
        } while (true);

    }


}
