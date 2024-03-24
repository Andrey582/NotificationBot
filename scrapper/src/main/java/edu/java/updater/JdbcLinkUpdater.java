package edu.java.updater;

import edu.java.configuration.ApplicationConfig;
import edu.java.database.jdbc.JdbcChatToLinkRepository;
import edu.java.database.jdbc.JdbcLinkRepository;
import edu.java.database.jdbc.model.Chat;
import edu.java.database.jdbc.model.ChatToLink;
import edu.java.database.jdbc.model.Link;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.processor.LinkProcessor;
import edu.java.service.BotService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcLinkUpdater implements LinkUpdater {

    JdbcLinkRepository jdbcLinkRepository;
    JdbcChatToLinkRepository jdbcChatToLinkRepository;
    ApplicationConfig applicationConfig;
    LinkProcessor linkProcessor;
    BotService botService;

    public JdbcLinkUpdater(
        JdbcLinkRepository jdbcLinkRepository,
        JdbcChatToLinkRepository jdbcChatToLinkRepository,
        ApplicationConfig applicationConfig,
        LinkProcessor linkProcessor,
        BotService botService
    ) {
        this.jdbcLinkRepository = jdbcLinkRepository;
        this.jdbcChatToLinkRepository = jdbcChatToLinkRepository;
        this.applicationConfig = applicationConfig;
        this.linkProcessor = linkProcessor;
        this.botService = botService;
    }

    @Override
    public void update() {
        List<Link> links = jdbcLinkRepository
            .findByTime(applicationConfig.scheduler().forceCheckDelay());

        for (Link link : links) {

            Map<Long, String> info = new HashMap<>();

            linkProcessor.processLink(link.getLinkUrl(), link.getLastCheckTime())
                .doOnSuccess(e -> jdbcLinkRepository.updateLastCheck(link.getLinkUrl()))
                .subscribe(e -> {
                        for (ChatToLink chatToLink : jdbcChatToLinkRepository.findAllChatByLink(link.getId())) {
                            Chat chat = chatToLink.getChat();
                            info.put(chat.getId(), chatToLink.getName());
                        }
                        botService.sendLinkUpdate(new BotLinkUpdateRequestDto(
                                link.getId(),
                                e.link(),
                                e.description(),
                                info
                            ))
                            .subscribe();
                    }
                );
        }
    }
}
