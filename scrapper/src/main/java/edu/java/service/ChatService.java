package edu.java.service;

import org.springframework.transaction.annotation.Transactional;

public interface ChatService {

    @Transactional
    void register(Long chatId);

    @Transactional
    void unregister(Long chatId);
}
