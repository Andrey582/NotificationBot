package edu.java.bot.configuration;

import edu.java.bot.dto.request.LinkUpdateRequestDto;
import edu.java.bot.interceptor.HeaderStrippingInterceptor;
import java.util.HashMap;
import java.util.List;
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
public class KafkaProducerConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder
            .name(applicationConfig.kafkaProducer().topicProp().name())
            .partitions(applicationConfig.kafkaProducer().topicProp().partitions())
            .replicas(applicationConfig.kafkaProducer().topicProp().replicas())
            .build();
    }

    @Bean
    public DefaultKafkaProducerFactory<String, LinkUpdateRequestDto> kafkaProducerFactory() {
        Map<String, Object> prop = new HashMap<>();

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.kafkaProducer().bootstrapServer());

        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, applicationConfig.kafkaProducer().batchSize());
        prop.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, applicationConfig.kafkaProducer().maxRequestSize());
        prop.put(ProducerConfig.LINGER_MS_CONFIG, applicationConfig.kafkaProducer().lingerMs());

        prop.put(ProducerConfig.ACKS_CONFIG, applicationConfig.kafkaProducer().acks());

        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);

        prop.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, List.of(HeaderStrippingInterceptor.class));

        return new DefaultKafkaProducerFactory<>(prop);
    }

    @Bean
    public KafkaTemplate<String, LinkUpdateRequestDto>
                    kafkaProducer(DefaultKafkaProducerFactory<String, LinkUpdateRequestDto> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

}
