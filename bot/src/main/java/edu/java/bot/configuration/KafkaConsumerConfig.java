package edu.java.bot.configuration;

import edu.java.bot.dto.request.LinkUpdateRequestDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public DefaultKafkaConsumerFactory<String, LinkUpdateRequestDto> kafkaConsumerFactory() {

        Map<String, Object> prop = new HashMap<>();

        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.kafkaConsumer().bootstrapServer());

        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        prop.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, applicationConfig.kafkaConsumer().fetchMaxByte());
        prop.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, applicationConfig.kafkaConsumer().maxPollRecords());
        prop.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, applicationConfig.kafkaConsumer().maxPollInterval());

        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, applicationConfig.kafkaConsumer().enableAutoCommit());

        prop.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, applicationConfig.kafkaConsumer().isolationLevel());

        prop.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, List.of(RoundRobinAssignor.class));

        prop.put(ConsumerConfig.GROUP_ID_CONFIG, applicationConfig.kafkaConsumer().groupId());

        return new DefaultKafkaConsumerFactory<>(
            prop,
            new StringDeserializer(),
            new JsonDeserializer<>(LinkUpdateRequestDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequestDto>
                kafkaListener(DefaultKafkaConsumerFactory<String, LinkUpdateRequestDto> kafkaConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequestDto> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(kafkaConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }
}

