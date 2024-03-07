package edu.java.service;

import edu.java.exception.exception.UserAlreadyRegisteredException;
import edu.java.exception.exception.UserNotRegisteredException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    List<Long> usersStub = new ArrayList<>();

    public void create(Long id) {
        if (usersStub.contains(id)) {
            throw new UserAlreadyRegisteredException("User already registered.");
        }
        usersStub.add(id);
    }

    public void  delete(Long id) {
        if (!usersStub.contains(id)) {
            throw new UserNotRegisteredException("User not registered.");
        }
        usersStub.remove(id);
    }

}
