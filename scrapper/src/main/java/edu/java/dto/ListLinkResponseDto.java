package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;

public record ListLinkResponseDto(

    @JsonProperty(value = "links", required = true)
    List<LinkResponseDto> links,

    @JsonProperty(value = "size", required = true)
    Integer size
) {
    public record LinkResponseDto(

        @JsonProperty(value = "id", required = true)
        Long id,

        @JsonProperty(value = "url", required = true)
        URI url
    ) {
    }
}
