package edu.java.updater;

import edu.java.configuration.ApplicationConfig;
import edu.java.database.jooq.repository.JooqChatToLinkRepository;
import edu.java.database.jooq.repository.JooqLinkRepository;
import edu.java.database.jooq.tables.pojos.Link;
import edu.java.database.jooq.tables.records.ChatRecord;
import edu.java.database.jooq.tables.records.ChatToLinkRecord;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.processor.LinkProcessor;
import edu.java.service.BotService;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JooqLinkUpdater implements LinkUpdater {

    @Autowired
    JooqLinkRepository jooqLinkRepository;
    @Autowired
    JooqChatToLinkRepository jooqChatToLinkRepository;
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    LinkProcessor linkProcessor;
    @Autowired
    BotService botService;

    @SneakyThrows
    @Override
    public void update() {
        List<Link> links = jooqLinkRepository
            .findByTime(applicationConfig.scheduler().forceCheckDelay());

        for (Link link : links) {

            Map<Long, String> info = new HashMap<>();
            URI uri = new URI(link.getLinkUrl());
            linkProcessor.processLink(uri, link.getLastCheckTime())
                .doOnSuccess(e -> jooqLinkRepository.updateLastCheck(uri))
                .subscribe(e -> {
                        for (Record2<ChatRecord, ChatToLinkRecord> chatToLink : jooqChatToLinkRepository.findAllChatByLink(link.getId())) {
                            info.put(chatToLink.component1().getChatId(), chatToLink.component2().getName());
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
