package edu.java.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.net.URI;

public record LinkRequestDto(
    @NotEmpty
    URI link
) {
}
