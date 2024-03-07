package edu.java.bot.controller;

import edu.java.bot.dto.LinkUpdateDto;
import edu.java.bot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @Autowired
    BotService botService;

    @PostMapping("/updates")
    public ResponseEntity<Void> updateLinks(@RequestBody LinkUpdateDto linkUpdateDto) {
        botService.update(linkUpdateDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
