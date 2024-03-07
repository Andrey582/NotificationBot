package edu.java.bot.service;

import edu.java.bot.dto.LinkUpdateDto;
import edu.java.bot.exception.EmptyChatIdsException;
import org.springframework.stereotype.Service;

@Service
public class BotService {

    public void update(LinkUpdateDto linkUpdateDto) {
        if (linkUpdateDto.tgChatIds().isEmpty()) {
            throw new EmptyChatIdsException("Chat id must be non empty.");
        }
    }
}
