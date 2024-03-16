package edu.java.updater;

import edu.java.configuration.ApplicationConfig;
import edu.java.database.model.Chat;
import edu.java.database.model.ChatToLink;
import edu.java.database.model.Link;
import edu.java.database.repository.ChatToLinkRepository;
import edu.java.database.repository.LinkRepository;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.processor.LinkProcessor;
import edu.java.service.BotService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JdbcLinkUpdater implements LinkUpdater {

    @Autowired
    LinkRepository linkRepository;
    @Autowired
    ChatToLinkRepository chatToLinkRepository;
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    LinkProcessor linkProcessor;
    @Autowired
    BotService botService;

    @Override
    public void update() {
        List<Link> links = linkRepository
            .findByTime(applicationConfig.scheduler().forceCheckDelay());

        for (Link link : links) {

            Map<Long, String> info = new HashMap<>();

            linkProcessor.processLink(link.getLinkUrl(), link.getLastCheckTime())
                .doOnSuccess(e -> linkRepository.updateLastCheck(link.getLinkUrl()))
                .subscribe(e -> {
                        for (ChatToLink chatToLink : chatToLinkRepository.findAllChatByLink(link.getId())) {
                            Chat chat = chatToLink.getChat();
                            info.put(chat.getChatId(), chatToLink.getName());
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
