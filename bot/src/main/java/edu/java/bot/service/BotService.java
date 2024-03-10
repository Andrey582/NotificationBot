package edu.java.bot.service;

import edu.java.bot.dto.request.LinkUpdateRequestDto;
import edu.java.bot.exception.EmptyChatIdsException;
import org.springframework.stereotype.Service;

@Service
public class BotService {

    public void update(LinkUpdateRequestDto linkUpdateRequestDto) {
        if (linkUpdateRequestDto.tgChatIds().isEmpty()) {
            throw new EmptyChatIdsException("Chat id must be non empty.");
        }
    }
}
