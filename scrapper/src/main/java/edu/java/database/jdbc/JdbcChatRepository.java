package edu.java.database.jdbc;

import edu.java.database.jdbc.model.Chat;
import edu.java.database.jdbc.model.mapper.ChatMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChatRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Chat add(Long chatId) {
        return jdbcTemplate.query(
            "insert into chat(id) values (?) on conflict do nothing returning *",
            new ChatMapper(),
            chatId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Chat remove(Long chatId) {
        return jdbcTemplate.query(
            "delete from chat where id = ? returning *",
            new ChatMapper(),
            chatId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Chat findChatById(Long chatId) {
        return jdbcTemplate.query(
            "select * from chat where id = ?",
            new ChatMapper(),
            chatId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query("select * from chat", new ChatMapper());
    }
}
