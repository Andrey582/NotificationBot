package edu.java.database;

import edu.java.database.jdbc.model.ChatToLink;
import jakarta.annotation.Nullable;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ChatToLinkRepository {

    @Transactional
    boolean add(Long chatId, Long linkId, @Nullable String name);

    @Transactional
    boolean remove(Long chatId, Long linkId);

    @Transactional
    boolean remove(Long chatId, String name);

    @Transactional(readOnly = true)
    List<ChatToLink> findAllLinkByChat(Long chatId);

    @Transactional(readOnly = true)
    List<ChatToLink> findAllLinksByName(Long chatId, String name);

    @Transactional(readOnly = true)
    List<ChatToLink> findAllChatByLink(Long linkId);

    @Transactional(readOnly = true)
    List<ChatToLink> findAll();
}
