package edu.java.configuration.access;

import edu.java.configuration.ApplicationConfig;
import edu.java.database.jpa.JpaChatRepository;
import edu.java.database.jpa.JpaChatToLinkRepository;
import edu.java.database.jpa.JpaLinkRepository;
import edu.java.processor.LinkProcessor;
import edu.java.service.BotService;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import edu.java.updater.JpaLinkUpdater;
import edu.java.updater.LinkUpdater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jpa")
public class JpaAccessConfig {

    @Bean
    public LinkService linkService(
        JpaLinkRepository jpaLinkRepository,
        JpaChatRepository jpaChatRepository,
        JpaChatToLinkRepository jpaChatToLinkRepository
    ) {
        return new JpaLinkService(jpaLinkRepository, jpaChatRepository, jpaChatToLinkRepository);
    }

    @Bean
    public ChatService chatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(
        JpaLinkRepository jpaLinkRepository,
        JpaChatToLinkRepository jpaChatToLinkRepository,
        ApplicationConfig applicationConfig,
        LinkProcessor linkProcessor,
        BotService botService
    ) {
        return new JpaLinkUpdater(
            jpaLinkRepository,
            jpaChatToLinkRepository,
            applicationConfig,
            linkProcessor,
            botService
        );
    }
}
