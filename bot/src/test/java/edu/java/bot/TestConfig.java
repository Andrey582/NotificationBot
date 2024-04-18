package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dto.request.LinkUpdateRequestDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class TestConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public DefaultKafkaProducerFactory<String, LinkUpdateRequestDto> kafkaTestProducerFactory() {
        Map<String, Object> prop = new HashMap<>();

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.kafkaProducer().bootstrapServer());

        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(prop);
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequestDto>
    kafkaTemplate(DefaultKafkaProducerFactory<String, LinkUpdateRequestDto> kafkaTestProducerFactory) {
        return new KafkaTemplate<>(kafkaTestProducerFactory);
    }

    @Bean
    public NewTopic newTestTopic() {
        return TopicBuilder
            .name("test_bot_dlq")
            .partitions(1)
            .replicas(1)
            .build();
    }
}
