package edu.java.bot.configuration.retry;


import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.retry.RetryProvider;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import reactor.util.retry.Retry;

@Configurable
@ConditionalOnProperty(prefix = "app", name = "retry-prop.retry-type", havingValue = "constant")
public class RetryConstantConfig {

    @Bean
    public Retry retry(ApplicationConfig applicationConfig) {
        return RetryProvider.constantRetry(
            applicationConfig.retryProp().attempt(),
            applicationConfig.retryProp().delay(),
            applicationConfig.retryProp().statusCode()
        );
    }
}
