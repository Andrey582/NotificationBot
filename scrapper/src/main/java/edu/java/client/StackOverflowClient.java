package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import org.springframework.stereotype.Component;

@Component
public class StackOverflowClient implements Client {

    private final String baseUrl;

    public StackOverflowClient(ApplicationConfig applicationConfig) {
        baseUrl = applicationConfig.stackoverflowBaseUrl();
    }

    @Override
    public String baseUrl() {
        return baseUrl;
    }
}
