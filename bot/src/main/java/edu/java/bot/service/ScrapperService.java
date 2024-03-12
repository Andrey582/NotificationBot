package edu.java.bot.service;

import edu.java.bot.dto.request.LinkRequestDto;
import edu.java.bot.dto.response.LinkResponseDto;
import edu.java.bot.dto.response.ListLinkResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ScrapperService {

    private static final String USER_PATH = "/tg-chat/";
    private static final String LINK_PATH = "/links";
    @Autowired
    WebClient scrapperClient;

    public Mono<Void> registerUser(Long chatId) {
        return scrapperClient
            .post()
            .uri(USER_PATH + chatId)
            .retrieve()
            .bodyToMono(Void.class);
    }

    public Mono<Void> deleteUser(Long chatId) {
        return scrapperClient
            .delete()
            .uri(USER_PATH + chatId)
            .retrieve()
            .bodyToMono(Void.class);
    }

    public Mono<ListLinkResponseDto> getAllLinks(Long chatId) {
        return scrapperClient
            .get()
            .uri(LINK_PATH)
            .header("id", String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinkResponseDto.class);
    }

    public Mono<LinkResponseDto> addLink(Long chatId, LinkRequestDto linkRequestDto) {
        return scrapperClient
            .post()
            .uri(LINK_PATH)
            .header("id", String.valueOf(chatId))
            .body(Mono.just(linkRequestDto), LinkRequestDto.class)
            .retrieve()
            .bodyToMono(LinkResponseDto.class);
    }

    public Mono<LinkResponseDto> deleteLink(Long chatId, LinkRequestDto linkRequestDto) {
        return scrapperClient
            .method(HttpMethod.DELETE)
            .uri(LINK_PATH)
            .header("id", String.valueOf(chatId))
            .body(Mono.just(linkRequestDto), LinkRequestDto.class)
            .retrieve()
            .bodyToMono(LinkResponseDto.class);
    }
}
