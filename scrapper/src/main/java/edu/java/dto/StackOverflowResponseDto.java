package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowResponseDto(

    @JsonProperty("items")
    List<StackOverflowItem> items
) {
    public record StackOverflowItem(
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivity
    ) {}
}
