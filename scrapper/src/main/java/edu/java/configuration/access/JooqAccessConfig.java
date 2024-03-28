package edu.java.configuration.access;

import edu.java.configuration.ApplicationConfig;
import edu.java.database.jooq.repository.JooqChatRepository;
import edu.java.database.jooq.repository.JooqChatToLinkRepository;
import edu.java.database.jooq.repository.JooqLinkRepository;
import edu.java.processor.LinkProcessor;
import edu.java.service.BotService;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jooq.JooqChatService;
import edu.java.service.jooq.JooqLinkService;
import edu.java.updater.JooqLinkUpdater;
import edu.java.updater.LinkUpdater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jooq")
public class JooqAccessConfig {

    @Bean
    public LinkService linkService(
        JooqLinkRepository jooqLinkRepository,
        JooqChatRepository jooqChatRepository,
        JooqChatToLinkRepository jooqChatToLinkRepository
    ) {
        return new JooqLinkService(jooqLinkRepository, jooqChatRepository, jooqChatToLinkRepository);
    }

    @Bean
    public ChatService chatService(JooqChatRepository jooqChatRepository) {
        return new JooqChatService(jooqChatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(
        JooqLinkRepository jooqLinkRepository,
        JooqChatToLinkRepository jooqChatToLinkRepository,
        ApplicationConfig applicationConfig,
        LinkProcessor linkProcessor,
        BotService botService
    ) {
        return new JooqLinkUpdater(
            jooqLinkRepository,
            jooqChatToLinkRepository,
            applicationConfig,
            linkProcessor,
            botService
        );
    }
}
