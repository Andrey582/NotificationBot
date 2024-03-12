package edu.java.bot.client;

import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ScrapperClient {

    private final String baseUrl;

    public ScrapperClient(ApplicationConfig applicationConfig) {
        baseUrl = applicationConfig.scrapperUrl();
    }

    @Bean("scrapperWebClient")
    public WebClient getClient() {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }
}
