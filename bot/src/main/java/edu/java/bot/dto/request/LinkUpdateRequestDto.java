package edu.java.bot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

public record LinkUpdateRequestDto(
    @NotNull
    Long id,
    @NotNull
    URI url,
    @NotBlank
    String description,
    @NotEmpty
    Map<Long, String> tgChatInfo
) {
}
