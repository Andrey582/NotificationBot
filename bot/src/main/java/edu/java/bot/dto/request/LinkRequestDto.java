package edu.java.bot.dto.request;

import java.net.URI;

public record LinkRequestDto(
    URI link,
    String name
) {
}
