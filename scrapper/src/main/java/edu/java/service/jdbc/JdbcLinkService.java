package edu.java.service.jdbc;

import edu.java.database.jdbc.JdbcChatRepository;
import edu.java.database.jdbc.JdbcChatToLinkRepository;
import edu.java.database.jdbc.JdbcLinkRepository;
import edu.java.database.jdbc.model.Chat;
import edu.java.database.jdbc.model.ChatToLink;
import edu.java.database.jdbc.model.Link;
import edu.java.dto.request.LinkRequestDto;
import edu.java.dto.response.LinkResponseDto;
import edu.java.dto.response.ListLinkResponseDto;
import edu.java.exception.exception.LinkAlreadyTrackedException;
import edu.java.exception.exception.LinkNotFoundException;
import edu.java.exception.exception.UserHasNoLinkException;
import edu.java.exception.exception.UserNotRegisteredException;
import edu.java.service.LinkService;
import java.util.List;

@SuppressWarnings("MultipleStringLiterals")

public class JdbcLinkService implements LinkService {

    JdbcLinkRepository jdbcLinkRepository;
    JdbcChatRepository jdbcChatRepository;
    JdbcChatToLinkRepository jdbcChatToLinkRepository;

    public JdbcLinkService(
        JdbcLinkRepository jdbcLinkRepository,
        JdbcChatRepository jdbcChatRepository,
        JdbcChatToLinkRepository jdbcChatToLinkRepository
    ) {
        this.jdbcLinkRepository = jdbcLinkRepository;
        this.jdbcChatRepository = jdbcChatRepository;
        this.jdbcChatToLinkRepository = jdbcChatToLinkRepository;
    }

    @Override
    public LinkResponseDto create(Long chatId, LinkRequestDto body) {
        Chat chat = jdbcChatRepository.findChatById(chatId);
        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }
        Link link = jdbcLinkRepository.add(body.link());
        if (link == null) {
            link = jdbcLinkRepository.findLinkByUrl(body.link());
        }
        if (!jdbcChatToLinkRepository.add(chat.getId(), link.getId(), body.name())) {
            throw new LinkAlreadyTrackedException("User already tracked this link.");
        }
        return new LinkResponseDto(chatId, link.getLinkUrl(), body.name());
    }

    @Override
    public LinkResponseDto delete(Long chatId, LinkRequestDto body) {
        Chat chat = jdbcChatRepository.findChatById(chatId);

        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        Link link = null;

        if (body.link() != null) {
            link = jdbcLinkRepository.findLinkByUrl(body.link());
        } else if (body.name() != null) {
            ChatToLink chatToLink = jdbcChatToLinkRepository.findAllLinksByName(chat.getId(), body.name())
                .stream()
                .findFirst()
                .orElse(null);
            link = chatToLink == null ? null : chatToLink.getLink();
        }

        if (link == null) {
            throw new LinkNotFoundException("Can`t find this link.");
        }

        if (!jdbcChatToLinkRepository.remove(chat.getId(), link.getId())) {
            throw new UserHasNoLinkException("User does not have this link.");
        }

        if (jdbcChatToLinkRepository.findAllChatByLink(link.getId()).isEmpty()) {
            jdbcLinkRepository.remove(link.getLinkUrl());
        }

        return new LinkResponseDto(chatId, link.getLinkUrl(), body.name());
    }

    @Override
    public ListLinkResponseDto getAll(Long chatId) {
        Chat chat = jdbcChatRepository.findChatById(chatId);
        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        List<ChatToLink> links = jdbcChatToLinkRepository
            .findAllLinkByChat(chat.getId());
        if (links.isEmpty()) {
            throw new UserHasNoLinkException("Links are empty.");
        }
        List<LinkResponseDto> linkResponseDtos = links
            .stream()
            .map(e -> new LinkResponseDto(chatId, e.getLink().getLinkUrl(), e.getName()))
            .toList();
        return new ListLinkResponseDto(linkResponseDtos, linkResponseDtos.size());
    }
}
