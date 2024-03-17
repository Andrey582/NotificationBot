package edu.java.service.jooq;

import edu.java.database.jooq.repository.JooqChatRepository;
import edu.java.database.jooq.repository.JooqChatToLinkRepository;
import edu.java.database.jooq.repository.JooqLinkRepository;
import edu.java.database.jooq.tables.pojos.Chat;
import edu.java.database.jooq.tables.pojos.Link;
import edu.java.database.jooq.tables.records.ChatToLinkRecord;
import edu.java.database.jooq.tables.records.LinkRecord;
import edu.java.dto.request.LinkRequestDto;
import edu.java.dto.response.LinkResponseDto;
import edu.java.dto.response.ListLinkResponseDto;
import edu.java.exception.exception.LinkAlreadyTrackedException;
import edu.java.exception.exception.LinkNotFoundException;
import edu.java.exception.exception.UserHasNoLinkException;
import edu.java.exception.exception.UserNotRegisteredException;
import edu.java.service.LinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.SneakyThrows;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JooqLinkService implements LinkService {

    @Autowired
    JooqLinkRepository jooqLinkRepository;
    @Autowired
    JooqChatRepository jooqChatRepository;
    @Autowired
    JooqChatToLinkRepository jooqChatToLinkRepository;

    @SneakyThrows
    @SuppressWarnings("MultipleStringLiterals")
    @Override
    public LinkResponseDto create(Long chatId, LinkRequestDto body) {
        Chat chat = jooqChatRepository.findChatById(chatId);
        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }
        Link link = jooqLinkRepository.add(body.link());
        if (link == null) {
            link = jooqLinkRepository.findLinkByUrl(body.link());
        }
        if (!jooqChatToLinkRepository.add(chat.getId(), link.getId(), body.name())) {
            throw new LinkAlreadyTrackedException("User already tracked this link.");
        }
        return new LinkResponseDto(chatId, new URI(link.getLinkUrl()), body.name());
    }

    @SneakyThrows
    @Override
    public LinkResponseDto delete(Long chatId, LinkRequestDto body) {
        Chat chat = jooqChatRepository.findChatById(chatId);

        if (jooqChatRepository.findChatById(chatId) == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        Link link = null;

        if (body.link() != null) {
            link = jooqLinkRepository.findLinkByUrl(body.link());
        } else if (body.name() != null) {
            link = jooqChatToLinkRepository.findAllLinksByName(chat.getId(), body.name())
                .stream()
                .findFirst()
                .orElse(null);
        }

        if (link == null) {
            throw new LinkNotFoundException("Can`t find this link.");
        }

        if (!jooqChatToLinkRepository.remove(chat.getId(), link.getId())) {
            throw new UserHasNoLinkException("User does not have this link.");
        }

        if (jooqChatToLinkRepository.findAllChatByLink(link.getId()).isEmpty()) {
            jooqLinkRepository.remove(new URI(link.getLinkUrl()));
        }

        return new LinkResponseDto(chatId, new URI(link.getLinkUrl()), body.name());
    }

    @Override
    public ListLinkResponseDto getAll(Long chatId) {
        Chat chat = jooqChatRepository.findChatById(chatId);
        if (chat == null) {
            throw new UserNotRegisteredException("Can`t find user.");
        }

        List<Record2<LinkRecord, ChatToLinkRecord>> linkByChat = jooqChatToLinkRepository
            .findAllLinkByChat(chat.getId());
        if (linkByChat.isEmpty()) {
            throw new UserHasNoLinkException("Links are empty.");
        }
        List<LinkResponseDto> linkResponseDtos = linkByChat
            .stream()
            .map(link -> {
                try {
                    return new LinkResponseDto(
                        chatId,
                        new URI(link.component1().getLinkUrl()),
                        link.component2().getName());

                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();
        return new ListLinkResponseDto(linkResponseDtos, linkResponseDtos.size());
    }
}
