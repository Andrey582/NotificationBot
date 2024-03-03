package edu.java.controller;

import edu.java.exception.exception.UserAlreadyRegisteredException;
import edu.java.exception.exception.UserNotRegisteredException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tg-chat/{id}")
public class ChatController {

    List<Long> usersStub = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Void> createUser(@PathVariable Long id) {
        if (usersStub.contains(id)) {
            throw new UserAlreadyRegisteredException("User already registered.");
        }
        usersStub.add(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!usersStub.contains(id)) {
            throw new UserNotRegisteredException("User not registered.");
        }
        usersStub.remove(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
