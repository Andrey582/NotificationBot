package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import java.net.URI;
import java.time.OffsetDateTime;

public record StackOverflowResponseItem(
    @JsonProperty("last_activity_date")
    OffsetDateTime lastActivity,
    @JsonProperty("creation_date")
    OffsetDateTime creationDate,
    @JsonProperty("last_edit_date")
    @Nullable
    OffsetDateTime lastEdit,
    @JsonProperty("link")
    URI postLink
) {}
