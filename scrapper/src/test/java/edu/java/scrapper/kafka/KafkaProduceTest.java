package edu.java.scrapper.kafka;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.jooq.meta.derby.sys.Sys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}
)
public class KafkaProduceTest {

    @Autowired
    private KafkaTemplate<String, BotLinkUpdateRequestDto> kafkaTemplate;

    private CountDownLatch countDownLatch = new CountDownLatch(1);


    @Test
    public void sendTest() throws InterruptedException {
        kafkaTemplate.send("test_scrapper", new BotLinkUpdateRequestDto(
            1L,
            URI.create("test.com"),
            "test",
            Map.of(
                1L, "test"
            )
        ));

        boolean messageConsumed = countDownLatch.await(15, TimeUnit.SECONDS);
        assertThat(messageConsumed).isTrue();
    }

    @KafkaListener(topics = "test_scrapper", groupId = "test", containerFactory = "kafkaListener")
    public void consume(BotLinkUpdateRequestDto requestDto) {
        countDownLatch.countDown();
    }

    @TestConfiguration
    static class ConsumerConfig{

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
}
