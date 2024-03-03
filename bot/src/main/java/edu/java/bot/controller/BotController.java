package edu.java.bot.controller;

import edu.java.bot.dto.LinkUpdateDto;
import edu.java.bot.exception.EmptyChatIdsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @PostMapping("/updates")
    public ResponseEntity<Void> updateLinks(@RequestBody LinkUpdateDto linkUpdateDto) {
        if (linkUpdateDto.tgChatIds().isEmpty()) {
            throw new EmptyChatIdsException("Chat id must be non empty.");
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
