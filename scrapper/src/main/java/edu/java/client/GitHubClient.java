package edu.java.client;

import edu.java.configuration.ApplicationConfig;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GitHubClient implements Client {

    private static final String BASE_GITHUB_URL = "https://api.github.com";
    private final String baseUrl;

    public GitHubClient(ApplicationConfig applicationConfig) {
        baseUrl = Objects.requireNonNullElse(applicationConfig.githubBaseUrl(), BASE_GITHUB_URL);
    }

    @Override
    @Bean("gitHubWebClient")
    public WebClient getWebClient() {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }

}
