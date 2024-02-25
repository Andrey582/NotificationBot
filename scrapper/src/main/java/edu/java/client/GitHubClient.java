package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import org.springframework.stereotype.Component;

@Component
public class GitHubClient implements Client {

    private final String baseUrl;

    public GitHubClient(ApplicationConfig applicationConfig) {
        baseUrl = applicationConfig.githubBaseUrl();
    }

    @Override
    public String baseUrl() {
        return baseUrl;
    }
}
