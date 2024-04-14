package edu.java.scrapper;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class TestConfig{

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public DefaultKafkaConsumerFactory<String, BotLinkUpdateRequestDto> kafkaConsumerFactory() {

        Map<String, Object> prop = new HashMap<>();

        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.kafkaProp().bootstrapServer());

        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);


        return new DefaultKafkaConsumerFactory<>(
            prop,
            new StringDeserializer(),
            new JsonDeserializer<>(BotLinkUpdateRequestDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BotLinkUpdateRequestDto>
    kafkaListener(DefaultKafkaConsumerFactory<String, BotLinkUpdateRequestDto> kafkaConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, BotLinkUpdateRequestDto> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(kafkaConsumerFactory);

        return factory;
    }

    @Bean
    public NewTopic newTestTopic() {
        return TopicBuilder
            .name("test_scrapper")
            .partitions(1)
            .replicas(1)
            .build();
    }
}
