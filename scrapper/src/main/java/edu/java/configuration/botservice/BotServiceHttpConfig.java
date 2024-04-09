package edu.java.configuration.botservice;

import edu.java.service.BotService;
import edu.java.service.bot.BotServiceHttp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class BotServiceHttpConfig {

    @Bean
    public BotService botService(WebClient botWebClient, Retry retry) {
        return new BotServiceHttp(botWebClient, retry);
    }
}
