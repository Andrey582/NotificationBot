package edu.java.processor;

import edu.java.dto.LinkChangesDto;
import edu.java.dto.response.StackOverflowResponseItem;
import edu.java.service.GitHubIntegrationService;
import edu.java.service.StackOverflowIntegrationService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Configurable;
import reactor.core.publisher.Mono;

@Configurable
public class StackOverflowQuestionProcessor extends LinkProcessor {

    public StackOverflowQuestionProcessor(
        LinkProcessor nextProcessor,
        GitHubIntegrationService gitHubIntegrationService,
        StackOverflowIntegrationService stackOverflowIntegrationService
    ) {
        super(nextProcessor, gitHubIntegrationService, stackOverflowIntegrationService);
    }

    @Override
    public Mono<LinkChangesDto> checkUpdate(URI link, OffsetDateTime lastCheckTime) {
        String[] split = link.getPath().split("/");
        return stackOverflowIntegrationService.getQuestionById(Long.valueOf(split[2]))
            .mapNotNull(response -> {
                if (response.items().isEmpty()) {
                    return null;
                } else {
                    StackOverflowResponseItem item = response.items().getFirst();
                    if (item.lastActivity().isAfter(lastCheckTime)) {
                        return checkUpdateType(item, link);
                    } else {
                        return null;
                    }
                }
            });
    }

    @Override
    public boolean validateLink(URI link) {
        String string = link.toString();
        Pattern pattern = Pattern.compile("https://stackoverflow.com/questions/\\d+");
        return pattern.matcher(string).find();
    }

    private LinkChangesDto checkUpdateType(StackOverflowResponseItem item, URI link) {
        if (item.lastActivity().isEqual(item.creationDate())) {
            return new LinkChangesDto(link, "Пост был создан");
        } else if (item.lastEdit() != null) {
            if (item.lastActivity().isEqual(item.lastEdit())) {
                return new LinkChangesDto(link, "Пост был изменен");
            }
        }
        return new LinkChangesDto(link, "Добавлен новый комментарий");
    }
}
