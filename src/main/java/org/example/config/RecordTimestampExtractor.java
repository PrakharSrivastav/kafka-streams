package org.example.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.example.model.avro.Measurement;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class RecordTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> record, long previousTimestamp) {
        String s = ((Measurement) record.value()).getTimestamp().toString();
        return DateTime.parse(s, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).getMillis();
    }
}