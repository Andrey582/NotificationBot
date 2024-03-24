package edu.java.configuration.access;

import edu.java.configuration.ApplicationConfig;
import edu.java.database.jdbc.JdbcChatRepository;
import edu.java.database.jdbc.JdbcChatToLinkRepository;
import edu.java.database.jdbc.JdbcLinkRepository;
import edu.java.processor.LinkProcessor;
import edu.java.service.BotService;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.updater.JdbcLinkUpdater;
import edu.java.updater.LinkUpdater;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jdbc")
public class JdbcAccessConfig {

    @Bean
    public LinkService linkService(
        JdbcLinkRepository linkRepository,
        JdbcChatRepository chatRepository,
        JdbcChatToLinkRepository chatToLinkRepository
    ) {
        return new JdbcLinkService(linkRepository, chatRepository, chatToLinkRepository);
    }

    @Bean
    public ChatService chatService(JdbcChatRepository jdbcChatRepository) {
        return new JdbcChatService(jdbcChatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(
        JdbcLinkRepository jdbcLinkRepository,
        JdbcChatToLinkRepository jdbcChatToLinkRepository,
        ApplicationConfig applicationConfig,
        LinkProcessor linkProcessor,
        BotService botService
    ) {
        return new JdbcLinkUpdater(
            jdbcLinkRepository,
            jdbcChatToLinkRepository,
            applicationConfig,
            linkProcessor,
            botService
        );
    }
}
