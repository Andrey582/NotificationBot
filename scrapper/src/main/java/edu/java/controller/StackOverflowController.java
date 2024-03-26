package edu.java.controller;

import edu.java.dto.response.StackOverflowResponseDto;
import edu.java.service.StackOverflowIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SuppressWarnings("MultipleStringLiterals")
@RestController
@RequestMapping("/stackoverflow")
public class StackOverflowController {

    @Autowired
    private StackOverflowIntegrationService stackOverflowIntegrationService;

    @GetMapping("/search/{question}")
    public Mono<StackOverflowResponseDto> getSearch(@PathVariable String question) {
        return stackOverflowIntegrationService.getSearch(question);
    }

    @GetMapping("/question/{question_id}")
    public Mono<StackOverflowResponseDto> getQuestionById(@PathVariable("question_id") Long questionId) {
        return stackOverflowIntegrationService.getQuestionById(questionId);
    }
}
