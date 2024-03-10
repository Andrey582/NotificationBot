package edu.java.dto.response;

import java.util.List;

public record StackOverflowResponseDto(
    List<StackOverflowResponseItem> items
) {

}
