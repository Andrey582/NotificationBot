package edu.java.processor;

import edu.java.dto.LinkChangesDto;
import edu.java.dto.response.StackOverflowResponseItem;
import edu.java.service.GitHubIntegrationService;
import edu.java.service.StackOverflowIntegrationService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Configurable;
import reactor.core.publisher.Mono;

@Configurable
public class StackOverflowSearchProcessor extends LinkProcessor {

    public StackOverflowSearchProcessor(
        LinkProcessor nextProcessor,
        GitHubIntegrationService gitHubIntegrationService,
        StackOverflowIntegrationService stackOverflowIntegrationService
    ) {
        super(nextProcessor, gitHubIntegrationService, stackOverflowIntegrationService);
    }

    @Override
    public Mono<LinkChangesDto> checkUpdate(URI link, OffsetDateTime lastCheckTime) {
        String question =
            Arrays.stream(link.getQuery().split("&")).filter(e -> e.startsWith("q")).findFirst().get().split("=")[1];
        return stackOverflowIntegrationService.getSearch(question)
            .mapNotNull(response -> {
                if (response.items().isEmpty()) {
                    return null;
                } else {
                    List<StackOverflowResponseItem> items =
                        response.items().stream().filter(item -> item.lastActivity().isAfter(lastCheckTime)).toList();
                    if (items.isEmpty()) {
                        return null;
                    } else {
                        return checkUpdateTypes(items, link);
                    }
                }
            });
    }

    @Override
    public boolean validateLink(URI link) {
        String string = link.toString();
        Pattern pattern = Pattern.compile("https://stackoverflow.com/search\\?q=[\\w+|+]+");
        return pattern.matcher(string).find();
    }

    private LinkChangesDto checkUpdateTypes(List<StackOverflowResponseItem> items, URI link) {
        String resultString = items.stream().map(item -> {
            if (item.lastActivity().isEqual(item.creationDate())) {
                return "Пост был создан - " + item.postLink().toString();
            } else if (item.lastEdit() != null) {
                if (item.lastActivity().isEqual(item.lastEdit())) {
                    return "Пост был изменен - " + item.postLink().toString();
                }
            }
            return "Добавлен новый комментарий - " + item.postLink().toString();
        }).collect(Collectors.joining("\n"));

        return new LinkChangesDto(link, resultString);

    }
}
