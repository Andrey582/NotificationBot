package edu.java.service.jdbc;

import edu.java.database.model.Chat;
import edu.java.database.model.ChatToLink;
import edu.java.database.model.Link;
import edu.java.database.repository.ChatRepository;
import edu.java.database.repository.ChatToLinkRepository;
import edu.java.database.repository.LinkRepository;
import edu.java.dto.request.LinkRequestDto;
import edu.java.dto.response.LinkResponseDto;
import edu.java.dto.response.ListLinkResponseDto;
import edu.java.exception.exception.LinkAlreadyTrackedException;
import edu.java.exception.exception.LinkNotFoundException;
import edu.java.exception.exception.UserHasNoLinkException;
import edu.java.exception.exception.UserNotRegisteredException;
import edu.java.service.LinkService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("MultipleStringLiterals")
@Service
public class JdbcLinkService implements LinkService {

    @Autowired
    LinkRepository linkRepository;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    ChatToLinkRepository chatToLinkRepository;

    @Override
    public LinkResponseDto create(Long chatId, LinkRequestDto body) {
        Chat chat = chatRepository.findChatById(chatId);
        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }
        Link link = linkRepository.add(body.link());
        if (link == null) {
            link = linkRepository.findLinkByUrl(body.link());
        }
        if (!chatToLinkRepository.add(chat.getId(), link.getId(), body.name())) {
            throw new LinkAlreadyTrackedException("User already tracked this link.");
        }
        return new LinkResponseDto(chatId, link.getLinkUrl(), body.name());
    }

    @Override
    public LinkResponseDto delete(Long chatId, LinkRequestDto body) {
        Chat chat = chatRepository.findChatById(chatId);

        if (chatRepository.findChatById(chatId) == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        Link link = null;

        if (body.link() != null) {
            link = linkRepository.findLinkByUrl(body.link());
        } else if (body.name() != null) {
            ChatToLink chatToLink = chatToLinkRepository.findAllLinksByName(chat.getId(), body.name())
                .stream()
                .findFirst()
                .orElse(null);
            link = chatToLink == null ? null : chatToLink.getLink();
        }

        if (link == null) {
            throw new LinkNotFoundException("Can`t find this link.");
        }

        if (!chatToLinkRepository.remove(chat.getId(), link.getId())) {
            throw new UserHasNoLinkException("User does not have this link.");
        }

        if (chatToLinkRepository.findAllChatByLink(link.getId()).isEmpty()) {
            linkRepository.remove(link.getLinkUrl());
        }

        return new LinkResponseDto(chatId, link.getLinkUrl(), body.name());
    }

    @Override
    public ListLinkResponseDto getAll(Long chatId) {
        Chat chat = chatRepository.findChatById(chatId);
        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        List<ChatToLink> links = chatToLinkRepository
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
