package edu.java.service.jpa;

import edu.java.database.jpa.JpaChatRepository;
import edu.java.database.jpa.JpaChatToLinkRepository;
import edu.java.database.jpa.JpaLinkRepository;
import edu.java.database.jpa.model.Chat;
import edu.java.database.jpa.model.ChatToLink;
import edu.java.database.jpa.model.Link;
import edu.java.dto.request.LinkRequestDto;
import edu.java.dto.response.LinkResponseDto;
import edu.java.dto.response.ListLinkResponseDto;
import edu.java.exception.exception.LinkAlreadyTrackedException;
import edu.java.exception.exception.LinkNotFoundException;
import edu.java.exception.exception.UserHasNoLinkException;
import edu.java.exception.exception.UserNotRegisteredException;
import edu.java.service.LinkService;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.SneakyThrows;

public class JpaLinkService implements LinkService {

    private JpaLinkRepository jpaLinkRepository;
    private JpaChatRepository jpaChatRepository;
    private JpaChatToLinkRepository jpaChatToLinkRepository;

    public JpaLinkService(
        JpaLinkRepository jpaLinkRepository,
        JpaChatRepository jpaChatRepository,
        JpaChatToLinkRepository jpaChatToLinkRepository
    ) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaChatRepository = jpaChatRepository;
        this.jpaChatToLinkRepository = jpaChatToLinkRepository;
    }

    @SneakyThrows
    @SuppressWarnings("MultipleStringLiterals")
    @Override
    public LinkResponseDto create(Long chatId, LinkRequestDto body) {
        Chat chat = jpaChatRepository.findChatById(chatId);
        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }
        Link link = jpaLinkRepository.findLinkByLinkUrl(body.link());
        if (link == null) {
            link = jpaLinkRepository.save(new Link(body.link(), OffsetDateTime.now()));
        }
        if (jpaChatToLinkRepository.findByChatIdAndLinkLinkUrl(chat.getId(), link.getLinkUrl()) != null) {
            throw new LinkAlreadyTrackedException("User already tracked this link.");
        } else {
            jpaChatToLinkRepository.save(new ChatToLink(chat, link, body.name()));
        }
        return new LinkResponseDto(chatId, link.getLinkUrl(), body.name());
    }

    @Override
    public LinkResponseDto delete(Long chatId, LinkRequestDto body) {
        Chat chat = jpaChatRepository.findChatById(chatId);

        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        Link link = null;

        if (body.link() != null) {
            link = jpaLinkRepository.findLinkByLinkUrl(body.link());
        } else if (body.name() != null) {
            link = jpaChatToLinkRepository.findAllByChatIdAndAndName(chat.getId(), body.name()).getLink();
        }

        if (link == null) {
            throw new LinkNotFoundException("Can`t find this link.");
        }

        if (jpaChatToLinkRepository.deleteByChatIdAndLinkId(chat.getId(), link.getId()) == 0) {
            throw new UserHasNoLinkException("User does not have this link.");
        }

        if (jpaChatToLinkRepository.findAllByLinkId(link.getId()).isEmpty()) {
            jpaLinkRepository.deleteByLinkUrl(link.getLinkUrl());
        }

        return new LinkResponseDto(chatId, link.getLinkUrl(), body.name());
    }

    @Override
    public ListLinkResponseDto getAll(Long chatId) {
        Chat chat = jpaChatRepository.findChatById(chatId);

        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        List<ChatToLink> allByChatId = jpaChatToLinkRepository
            .findAllByChatId(chat.getId());

        if (allByChatId.isEmpty()) {
            throw new UserHasNoLinkException("Links are empty.");
        }

        List<LinkResponseDto> linkResponseDtos = allByChatId
            .stream()
            .map(link ->
                new LinkResponseDto(
                    chatId,
                    link.getLink().getLinkUrl(),
                    link.getName())
            )
            .toList();
        return new ListLinkResponseDto(linkResponseDtos, linkResponseDtos.size());
    }

}
