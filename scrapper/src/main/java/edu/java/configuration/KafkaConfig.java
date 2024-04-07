package edu.java.configuration;

import edu.java.dto.request.BotLinkUpdateRequestDto;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder
            .name(applicationConfig.kafkaProp().topicProp().name())
            .partitions(applicationConfig.kafkaProp().topicProp().partitions())
            .replicas(applicationConfig.kafkaProp().topicProp().replicas())
            .build();
    }

    @Bean
    public DefaultKafkaProducerFactory<String, BotLinkUpdateRequestDto> kafkaProducerFactory() {
        Map<String, Object> prop = new HashMap<>();

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.kafkaProp().bootstrapServer());

        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, applicationConfig.kafkaProp().batchSize());
        prop.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, applicationConfig.kafkaProp().maxRequestSize());
        prop.put(ProducerConfig.LINGER_MS_CONFIG, applicationConfig.kafkaProp().lingerMs());

        prop.put(ProducerConfig.ACKS_CONFIG, applicationConfig.kafkaProp().acks());

        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);

        return new DefaultKafkaProducerFactory<>(prop);
    }

    @Bean
    public KafkaTemplate<String, BotLinkUpdateRequestDto>
                kafkaTemplate(DefaultKafkaProducerFactory<String, BotLinkUpdateRequestDto> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

}
