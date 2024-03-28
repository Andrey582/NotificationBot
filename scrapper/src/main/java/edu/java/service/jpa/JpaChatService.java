package edu.java.service.jpa;

import edu.java.database.jpa.JpaChatRepository;
import edu.java.database.jpa.model.Chat;
import edu.java.exception.exception.UserAlreadyRegisteredException;
import edu.java.exception.exception.UserNotRegisteredException;
import edu.java.service.ChatService;

public class JpaChatService implements ChatService {

    private JpaChatRepository jpaChatRepository;

    public JpaChatService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public void register(Long chatId) {
        if (jpaChatRepository.existsById(chatId)) {
            throw new UserAlreadyRegisteredException("Can`t register user.");
        }
        jpaChatRepository.save(new Chat(chatId));
    }

    @Override
    public void unregister(Long chatId) {
        if (!jpaChatRepository.existsById(chatId)) {
            throw new UserNotRegisteredException("Can`t delete user.");
        }
        jpaChatRepository.deleteById(chatId);
    }
}
