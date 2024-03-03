package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;

public record ListLinkResponseDto(

    @JsonProperty("links")
    List<LinkResponseDto> links,

    @JsonProperty("size")
    Integer size
) {
    public record LinkResponseDto(

        @JsonProperty("id")
        Long id,

        @JsonProperty("url")
        URI url
    ) {
    }
}
