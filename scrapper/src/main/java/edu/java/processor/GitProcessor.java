package edu.java.processor;

import edu.java.dto.LinkChangesDto;
import edu.java.dto.response.GitHubResponseDto;
import edu.java.service.GitHubIntegrationService;
import edu.java.service.StackOverflowIntegrationService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import reactor.core.publisher.Mono;

public class GitProcessor extends LinkProcessor {

    public GitProcessor(
        LinkProcessor nextProcessor,
        GitHubIntegrationService gitHubIntegrationService,
        StackOverflowIntegrationService stackOverflowIntegrationService
    ) {
        super(nextProcessor, gitHubIntegrationService, stackOverflowIntegrationService);
    }

    @Override
    public Mono<LinkChangesDto> checkUpdate(URI link, OffsetDateTime lastCheckTime) {
        String[] split = link.getPath().split("/");
         return gitHubIntegrationService.getEvents(split[1], split[2])
            .mapNotNull(response -> {
                List<GitHubResponseDto> body = response.getBody();
                if (body.isEmpty()) {
                    return null;
                } else {
                    List<GitHubResponseDto> dtoList = body
                        .stream()
                        .filter(e -> e.create().isAfter(lastCheckTime))
                        .toList();

                    if (dtoList.isEmpty()) {
                        return null;
                    } else {
                        return new LinkChangesDto(
                            link,
                            dtoList
                                .stream()
                                .map(item -> checkUpdateType(item.type()))
                                .collect(Collectors.joining("\n"))
                        );
                    }
                }
            });
    }

    @Override
    public boolean validateLink(URI link) {
        String string = link.toString();
        Pattern pattern = Pattern.compile("https://github.com/[\\w+|-]+/[\\w+|-]+");
        return pattern.matcher(string).find();
    }

    private String checkUpdateType(String type) {
        return switch (type) {
            case "IssueCommentEvent" ->  "Новый комментарий.";
            case "PushEvent" -> "Запушили коммит.";
            case "CommitCommentEvent" -> "Прокомментирован коммит.";
            case "CreateEvent" -> "Создана новая ветка или тэг.";
            case "DeleteEvent" -> "Удалена ветка или тэг.";
            case "ForkEvent" -> "Репозиторий был форкнут.";
            case "PullRequestEvent" -> "Создан пулл реквест.";
            case "PullRequestReviewEvent" -> "Произшло ревью пулл реквеста.";
            case "PullRequestReviewCommentEvent" -> "Оставлен комментарий в пулл реквесте.";
            default -> "Не удалось распознать тип обновления.";
        };
    }
}
