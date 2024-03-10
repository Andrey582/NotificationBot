package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverflowResponseItem(
    @JsonProperty("last_activity_date")
    OffsetDateTime lastActivity
) {}
