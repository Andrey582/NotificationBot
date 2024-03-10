package edu.java.bot.dto.response;

import java.net.URI;

public record LinkResponseDto(
    Long id,
    URI url
) {
}
