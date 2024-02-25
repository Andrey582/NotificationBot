package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubResponseDto(

    @JsonProperty("created_at")
    OffsetDateTime create
) {
}
