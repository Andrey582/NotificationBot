package edu.java.updater;

import edu.java.configuration.ApplicationConfig;
import edu.java.database.jpa.JpaChatToLinkRepository;
import edu.java.database.jpa.JpaLinkRepository;
import edu.java.database.jpa.model.ChatToLink;
import edu.java.database.jpa.model.Link;
import edu.java.dto.request.BotLinkUpdateRequestDto;
import edu.java.processor.LinkProcessor;
import edu.java.service.BotService;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaLinkUpdater implements LinkUpdater {

    private JpaLinkRepository jpaLinkRepository;
    private JpaChatToLinkRepository jpaChatToLinkRepository;
    private ApplicationConfig applicationConfig;
    private LinkProcessor linkProcessor;
    private BotService botService;

    public JpaLinkUpdater(
        JpaLinkRepository jpaLinkRepository,
        JpaChatToLinkRepository jpaChatToLinkRepository,
        ApplicationConfig applicationConfig,
        LinkProcessor linkProcessor,
        BotService botService
    ) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaChatToLinkRepository = jpaChatToLinkRepository;
        this.applicationConfig = applicationConfig;
        this.linkProcessor = linkProcessor;
        this.botService = botService;
    }

    @Override
    public void update() {
        List<Link> links = jpaLinkRepository
            .findByLastCheckTimeIsAfterDuration(applicationConfig.scheduler().forceCheckDelay());

        for (Link link : links) {

            Map<Long, String> info = new HashMap<>();
            URI uri = link.getLinkUrl();
            linkProcessor.processLink(uri, link.getLastCheckTime())
                .doOnSuccess(e -> jpaLinkRepository.updateLastCheckByLinkUrl(uri))
                .subscribe(e -> {
                        for (ChatToLink chatToLink : jpaChatToLinkRepository.findAllByLinkId(link.getId())) {
                            info.put(chatToLink.getChat().getId(), chatToLink.getName());
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
