package edu.java.bot.controller;

import edu.java.bot.dto.request.LinkUpdateRequestDto;
import edu.java.bot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @Autowired
    BotService botService;

    @PostMapping("/updates")
    public ResponseEntity<Void> updateLinks(@RequestBody LinkUpdateRequestDto linkUpdateRequestDto) {
        botService.update(linkUpdateRequestDto);
        return ResponseEntity.ok().build();
    }
}
