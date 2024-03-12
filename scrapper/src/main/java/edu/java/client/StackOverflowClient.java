package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class StackOverflowClient implements Client {

    private static final String BASE_STACKOVERFLOW_STRING = "https://api.stackexchange.com/2.3";
    private final String baseUrl;

    public StackOverflowClient(ApplicationConfig applicationConfig) {
        baseUrl = Objects.requireNonNullElse(applicationConfig.stackoverflowBaseUrl(), BASE_STACKOVERFLOW_STRING);
    }

    @Override
    @Bean("stackOverflowWebClient")
    public WebClient getWebClient() {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }
}
