package edu.java.configuration;

import edu.java.controller.GitHubController;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@ComponentScan(basePackageClasses = GitHubController.class)
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,

    @NotNull
    String stackoverflowBaseUrl,

    @NotNull
    String githubBaseUrl
) {

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }
}
