package edu.java.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,
    String stackoverflowBaseUrl,
    String githubBaseUrl,
    @NotEmpty
    String botBaseUrl,
    @NotEmpty
    String githubAccessToken,
    AccessType databaseAccessType,
    @NotNull
    KafkaProp kafkaProp,
    @NotNull
    Boolean useQueue,
    @NotNull
    RetryProp retryProp
) {

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public enum AccessType {
        JDBC,
        JPA,
        JOOQ
    }

    public record KafkaProp(
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
