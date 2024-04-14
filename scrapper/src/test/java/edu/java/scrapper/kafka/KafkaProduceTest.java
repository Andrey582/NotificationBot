package edu.java.scrapper.kafka;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
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
}
