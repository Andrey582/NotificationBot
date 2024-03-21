package edu.java.service;

import edu.java.dto.request.LinkRequestDto;
import edu.java.dto.response.LinkResponseDto;
import edu.java.dto.response.ListLinkResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface LinkService {

    @Transactional
    LinkResponseDto create(Long chatId, LinkRequestDto body);

    @Transactional
    LinkResponseDto delete(Long chatId, LinkRequestDto body);

    @Transactional(readOnly = trueg)
    ListLinkResponseDto getAll(Long chatId);
}
