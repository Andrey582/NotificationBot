package edu.java.dto.request;

import java.net.URI;
import java.util.Map;

public record BotLinkUpdateRequestDto(
    Long id,
    URI url,
    String description,
    Map<Long, String> tgChatInfo
) {
}
