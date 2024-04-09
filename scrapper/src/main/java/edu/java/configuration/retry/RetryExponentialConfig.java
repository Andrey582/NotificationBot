package edu.java.configuration.retry;

import edu.java.configuration.ApplicationConfig;
import edu.java.retry.RetryProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "retry-prop.retry-type", havingValue = "exponential")
public class RetryExponentialConfig {

    @Bean
    public Retry retry(ApplicationConfig applicationConfig) {
        return RetryProvider.exponentialRetry(
            applicationConfig.retryProp().attempt(),
            applicationConfig.retryProp().delay(),
            applicationConfig.retryProp().statusCode()
        );
    }
}
