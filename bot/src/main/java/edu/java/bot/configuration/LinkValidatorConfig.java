package edu.java.bot.configuration;

import edu.java.bot.validate.GitHubReposPattern;
import edu.java.bot.validate.LinkValidator;
import edu.java.bot.validate.StackOverflowQuestionPattern;
import edu.java.bot.validate.StackOverflowSearchPattern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkValidatorConfig {

    @Bean
    public LinkValidator linkValidator() {
        StackOverflowSearchPattern stackOverflowSearchPattern = new StackOverflowSearchPattern(null);
        StackOverflowQuestionPattern stackOverflowQuestionPattern =
            new StackOverflowQuestionPattern(stackOverflowSearchPattern);
        GitHubReposPattern gitHubReposPattern = new GitHubReposPattern(stackOverflowQuestionPattern);

        return gitHubReposPattern;
    }
}
