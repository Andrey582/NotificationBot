package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record LinkResponseDto(
    Long id,
    URI url
) {
}
