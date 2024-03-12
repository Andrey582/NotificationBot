package edu.java.dto.response;

import java.net.URI;

public record LinkResponseDto(
    Long id,
    URI url
) {
}
