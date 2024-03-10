package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BotClient implements Client {

    private final String baseUrl;

    public BotClient(ApplicationConfig applicationConfig) {
        baseUrl = applicationConfig.botBaseUrl();
    }

    @Override
    @Bean("botWebClient")
    public WebClient getWebClient() {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }
}
