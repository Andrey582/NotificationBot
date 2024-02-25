package edu.java.configuration;

import edu.java.client.Client;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Autowired
    private Map<String, Client> clientMap;

    @Bean
    public WebClient gitHubWebClient() {
        return WebClient
            .builder()
            .baseUrl(clientMap.get("gitHubClient").baseUrl())
            .build();
    }

    @Bean
    public WebClient stackOverflowWebClient() {
        return WebClient
            .builder()
            .baseUrl(clientMap.get("stackOverflowClient").baseUrl())
            .build();
    }
}
