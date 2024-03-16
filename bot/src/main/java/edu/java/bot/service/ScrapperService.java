package edu.java.bot.service;

import edu.java.bot.dto.request.LinkRequestDto;
import edu.java.bot.dto.response.LinkResponseDto;
import edu.java.bot.dto.response.ListLinkResponseDto;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ScrapperService {

    private static final String USER_PATH = "/tg-chat/";
    private static final String LINK_PATH = "/links";
    @Autowired
    WebClient scrapperClient;

    public Mono<ResponseEntity<Void>> registerUser(Long chatId) {
        return scrapperClient
            .post()
            .uri(USER_PATH + chatId)
            .retrieve()
            .toBodilessEntity()
            .doOnError(throwable -> log.error(throwable.getMessage()))
            .onErrorResume(throwable -> Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<ResponseEntity<Void>> deleteUser(Long chatId) {
        return scrapperClient
            .delete()
            .uri(USER_PATH + chatId)
            .retrieve()
            .toBodilessEntity();
    }

    public Mono<ListLinkResponseDto> getAllLinks(Long chatId) {
        return scrapperClient
            .get()
            .uri(LINK_PATH)
            .header("id", String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinkResponseDto.class)
            .doOnSuccess(e -> log.info("Get link success"))
            .doOnError(throwable -> log.error(throwable.getMessage()))
            .onErrorReturn(new ListLinkResponseDto(new ArrayList<>(), 0));
    }

    public Mono<LinkResponseDto> addLink(Long chatId, LinkRequestDto linkRequestDto) {
        return scrapperClient
            .post()
            .uri(LINK_PATH)
            .header("id", String.valueOf(chatId))
            .body(Mono.just(linkRequestDto), LinkRequestDto.class)
            .retrieve()
            .bodyToMono(LinkResponseDto.class)
            .doOnSuccess(e -> log.info("Add link success"))
            .doOnError(throwable -> log.error(throwable.getMessage()))
            .onErrorReturn(new LinkResponseDto(null, null, null));
    }

    public Mono<LinkResponseDto> deleteLink(Long chatId, LinkRequestDto linkRequestDto) {
        return scrapperClient
            .method(HttpMethod.DELETE)
            .uri(LINK_PATH)
            .header("id", String.valueOf(chatId))
            .body(Mono.just(linkRequestDto), LinkRequestDto.class)
            .retrieve()
            .bodyToMono(LinkResponseDto.class)
            .doOnSuccess(e -> log.info("Delete link success"))
            .doOnError(throwable -> log.error(throwable.getMessage()))
            .onErrorReturn(new LinkResponseDto(null, null, null));
    }
}
