package edu.java.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ListLinkResponseDto(
    List<LinkResponseDto> links,
    Integer size
) {

}
