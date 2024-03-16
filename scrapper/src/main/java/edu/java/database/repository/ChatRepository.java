package edu.java.database.repository;

import edu.java.database.model.Chat;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ChatRepository {

    @Transactional
    Chat add(Long chatId);

    @Transactional
    Chat remove(Long chatId);

    @Transactional(readOnly = true)
    Chat findChatById(Long chatId);

    @Transactional(readOnly = true)
    List<Chat> findAll();
}
