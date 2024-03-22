package edu.java.processor;

import edu.java.dto.LinkChangesDto;
import edu.java.service.GitHubIntegrationService;
import edu.java.service.StackOverflowIntegrationService;
import java.net.URI;
import java.time.OffsetDateTime;
import reactor.core.publisher.Mono;


public abstract class LinkProcessor {

    protected LinkProcessor nextProcessor;
    protected GitHubIntegrationService gitHubIntegrationService;
    protected StackOverflowIntegrationService stackOverflowIntegrationService;

    public LinkProcessor(
        LinkProcessor nextProcessor,
        GitHubIntegrationService gitHubIntegrationService,
        StackOverflowIntegrationService stackOverflowIntegrationService
    ) {
        this.nextProcessor = nextProcessor;
        this.gitHubIntegrationService = gitHubIntegrationService;
        this.stackOverflowIntegrationService = stackOverflowIntegrationService;
    }

    public abstract Mono<LinkChangesDto> checkUpdate(URI link, OffsetDateTime lastCheckTime);

    public abstract boolean validateLink(URI link);

    public Mono<LinkChangesDto> processLink(URI link, OffsetDateTime lastCheckTime) {
        if (validateLink(link)) {
            return checkUpdate(link, lastCheckTime);
        }
        return nextProcessor.processLink(link, lastCheckTime);
    }

}
