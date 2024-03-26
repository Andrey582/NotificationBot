package edu.java.service;

import edu.java.dto.request.BotLinkUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BotService {

    @Autowired
    private WebClient botWebClient;

    public Mono<Void> sendLinkUpdate(BotLinkUpdateRequestDto linkUpdateRequestDto) {
        return botWebClient
            .post()
            .uri("/updates")
            .body(Mono.just(linkUpdateRequestDto), BotLinkUpdateRequestDto.class)
            .retrieve()
            .bodyToMono(Void.class);
    }
}
