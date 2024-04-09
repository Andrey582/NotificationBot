package edu.java.service;

import edu.java.dto.request.BotLinkUpdateRequestDto;
import reactor.core.publisher.Mono;

public interface BotService {

    Mono<Void> sendLinkUpdate(BotLinkUpdateRequestDto linkUpdateRequestDto);
}
