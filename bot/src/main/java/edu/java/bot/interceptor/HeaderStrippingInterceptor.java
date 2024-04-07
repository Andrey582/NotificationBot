package edu.java.bot.interceptor;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.support.KafkaHeaders;

public class HeaderStrippingInterceptor<K, V> implements ProducerInterceptor<K, V> {

    @Override
    public ProducerRecord<K, V> onSend(ProducerRecord<K, V> producerRecord) {
        Header header = producerRecord.headers().lastHeader(KafkaHeaders.EXCEPTION_STACKTRACE);
        if (header != null) {
            producerRecord.headers().remove(KafkaHeaders.EXCEPTION_STACKTRACE);
        }
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
