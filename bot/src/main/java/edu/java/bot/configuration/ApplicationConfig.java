package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotEmpty
    String scrapperUrl,
    @NotNull
    KafkaConsumerProperty kafkaConsumer,
    @NotNull
    KafkaProducerProperty kafkaProducer,
    @NotNull
    RetryProp retryProp
) {
    public record KafkaConsumerProperty(
        @NotEmpty
        String bootstrapServer,
        @NotNull
        Integer fetchMaxByte,
        @NotNull
        Integer maxPollRecords,
        @NotNull
        Boolean enableAutoCommit,
        @NotEmpty
        String isolationLevel,
        @NotEmpty
        String groupId
    ) {

    }

    public record KafkaProducerProperty(
        @NotNull
        Integer batchSize,
        @NotNull
        Integer maxRequestSize,
        @NotNull
        Long lingerMs,
        @NotEmpty
        String acks,
        @NotNull
        TopicProp topicProp,
        @NotNull
        String bootstrapServer
    ) {

        public record TopicProp(
            @NotEmpty
            String name,
            @NotNull
            Integer partitions,
            @NotNull
            Integer replicas
        ) {

        }
    }

    public record RetryProp(
        @NotNull
        RetryType retryType,
        @NotNull
        Long attempt,
        @NotNull
        Duration delay,
        Duration linearStep,
        @NotNull
        List<Integer> statusCode
    ) {

        public enum RetryType {
            CONSTANT,
            LINEAR,
            EXPONENTIAL
        }
    }
}
