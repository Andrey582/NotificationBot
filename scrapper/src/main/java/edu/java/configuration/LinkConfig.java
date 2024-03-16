package edu.java.configuration;

import edu.java.processor.GitProcessor;
import edu.java.processor.LinkProcessor;
import edu.java.processor.StackOverflowQuestionProcessor;
import edu.java.processor.StackOverflowSearchProcessor;
import edu.java.service.GitHubIntegrationService;
import edu.java.service.StackOverflowIntegrationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkConfig {

    @Bean
    public LinkProcessor linkProcessor(
        GitHubIntegrationService gitHubIntegrationService,
        StackOverflowIntegrationService stackOverflowIntegrationService
    ) {
        GitProcessor gitProcessor = new GitProcessor(
            null,
            gitHubIntegrationService,
            stackOverflowIntegrationService
        );
        StackOverflowQuestionProcessor stackOverflowQuestionProcessor = new StackOverflowQuestionProcessor(
            gitProcessor,
            gitHubIntegrationService,
            stackOverflowIntegrationService
        );
        StackOverflowSearchProcessor stackOverflowSearchProcessor = new StackOverflowSearchProcessor(
            stackOverflowQuestionProcessor,
            gitHubIntegrationService,
            stackOverflowIntegrationService
        );

        return stackOverflowSearchProcessor;
    }
}
