package edu.java.controller;

import edu.java.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tg-chat/{id}")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping
    public ResponseEntity<Void> createUser(@PathVariable Long id) {
        chatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        chatService.unregister(id);
        return ResponseEntity.ok().build();
    }
}
