package edu.java.service.jooq;

import edu.java.database.jooq.repository.JooqChatRepository;
import edu.java.exception.exception.UserAlreadyRegisteredException;
import edu.java.exception.exception.UserNotRegisteredException;
import edu.java.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JooqChatService implements ChatService {

    @Autowired
    JooqChatRepository jooqChatRepository;

    @Override
    public void register(Long chatId) {
        if (jooqChatRepository.add(chatId) == null) {
            throw new UserAlreadyRegisteredException("Can`t register user.");
        }
    }

    @Override
    public void unregister(Long chatId) {
        if (jooqChatRepository.remove(chatId) == null) {
            throw new UserNotRegisteredException("Can`t delete user.");
        }
    }
}
