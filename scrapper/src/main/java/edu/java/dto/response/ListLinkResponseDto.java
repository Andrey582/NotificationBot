package edu.java.dto.response;

import java.util.List;

public record ListLinkResponseDto(
    List<LinkResponseDto> links,
    Integer size
) {

}
