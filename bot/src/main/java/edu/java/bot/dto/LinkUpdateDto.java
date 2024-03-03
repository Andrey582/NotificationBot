package edu.java.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;

public record LinkUpdateDto(

    @JsonProperty(value = "id", required = true)
    Long id,

    @JsonProperty(value = "url", required = true)
    URI url,

    @JsonProperty(value = "description", required = true)
    String description,

    @JsonProperty(value = "tgChatIds", required = true)
    List<Long> tgChatIds
) {
}
