package edu.java.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public record BotLinkUpdateRequestDto(
    Long id,
    URI url,
    String description,
    List<Long> tgChatIds
) {
}
