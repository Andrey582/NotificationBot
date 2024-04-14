package edu.java.bot.kafka;

import edu.java.bot.dto.request.LinkUpdateRequestDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.annotation.DirtiesContext;
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
    brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"},
    topics = "test_bot")
public class KafkaDlqTest {

    @Autowired
    private KafkaTemplate<String, LinkUpdateRequestDto> kafkaTemplate;

    CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void sendTest() throws InterruptedException {

        kafkaTemplate.send("test_bot", new LinkUpdateRequestDto(
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


    @RetryableTopic(
        attempts = "1",
        kafkaTemplate = "kafkaProducer",
        dltTopicSuffix = "_dlq",
        include = RuntimeException.class
    )
    @KafkaListener(topics = "test_bot", groupId = "test", containerFactory = "kafkaListener")
    public void consume(@Payload LinkUpdateRequestDto requestDto, Acknowledgment ack) {
        throw new RuntimeException("send to dlq");
    }

    @KafkaListener(topics = "test_bot_dlq", groupId = "test_dlq", containerFactory = "kafkaListener")
    public void consumeDlq(@Payload LinkUpdateRequestDto requestDto, Acknowledgment ack) {
        countDownLatch.countDown();
        ack.acknowledge();
    }

    @TestConfiguration
    static class ConsumerConfig{

        @Bean
        public DefaultKafkaProducerFactory<String, LinkUpdateRequestDto> kafkaTestProducerFactory() {
            Map<String, Object> prop = new HashMap<>();

            prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "PLAINTEXT://localhost:9092");

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
        public NewTopic newTopic() {
            return TopicBuilder
                .name("test_bot_dlq")
                .partitions(1)
                .replicas(1)
                .build();
        }
    }
}
