package edu.java.service;

import edu.java.dto.request.LinkRequestDto;
import edu.java.dto.response.LinkResponseDto;
import edu.java.dto.response.ListLinkResponseDto;

public interface LinkService {

    LinkResponseDto create(Long chatId, LinkRequestDto body);

    LinkResponseDto delete(Long chatId, LinkRequestDto body);

    ListLinkResponseDto getAll(Long chatId);
}
