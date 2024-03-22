package edu.java.dto;

import java.net.URI;

public record LinkChangesDto(
    URI link,
    String description

) {
}
