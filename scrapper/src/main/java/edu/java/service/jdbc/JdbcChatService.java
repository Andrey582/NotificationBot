package edu.java.service.jdbc;

import edu.java.database.jdbc.JdbcChatRepository;
import edu.java.exception.exception.UserAlreadyRegisteredException;
import edu.java.exception.exception.UserNotRegisteredException;
import edu.java.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class JdbcChatService implements ChatService {

    @Autowired
    JdbcChatRepository jdbcChatRepository;

    @Override
    public void register(Long chatId) {
        if (jdbcChatRepository.add(chatId) == null) {
            throw new UserAlreadyRegisteredException("Can`t register user.");
        }
    }

    @Override
    public void unregister(Long chatId) {
        if (jdbcChatRepository.remove(chatId) == null) {
            throw new UserNotRegisteredException("Can`t delete user.");
        }
    }


}