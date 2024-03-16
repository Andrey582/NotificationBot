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
                                .map(GitHubResponseDto::type)
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
}
