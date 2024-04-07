package edu.java.configuration.botservice;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.service.BotService;
import edu.java.service.bot.BotServiceKafka;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class BotServiceKafkaConfig {

    @Bean
    public BotService botService(
        ApplicationConfig applicationConfig,
        KafkaTemplate<String, BotLinkUpdateRequestDto> kafkaTemplate
    ) {
        return new BotServiceKafka(applicationConfig, kafkaTemplate);
    }
}
