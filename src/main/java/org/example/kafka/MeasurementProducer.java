package org.example.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.example.model.avro.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MeasurementProducer {

    @Autowired
    private KafkaTemplate<String, Measurement> kafkaTemplate;

    @Autowired
    private NewTopic topic1;

    public void sendMessage(String key, Measurement value) {
        this.kafkaTemplate.send(topic1.name(), key, value);
    }

}
