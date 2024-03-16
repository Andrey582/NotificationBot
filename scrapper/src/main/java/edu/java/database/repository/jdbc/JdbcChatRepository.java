package edu.java.database.repository.jdbc;

import edu.java.database.model.Chat;
import edu.java.database.model.mapper.ChatMapper;
import edu.java.database.repository.ChatRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcChatRepository implements ChatRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Chat add(Long chatId) {
        return jdbcTemplate.query(
            "insert into chat(chat_id) values (?) on conflict do nothing returning *",
            new ChatMapper(),
            chatId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public Chat remove(Long chatId) {
        return jdbcTemplate.query(
            "delete from chat where chat_id = ? returning *",
            new ChatMapper(),
            chatId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public Chat findChatById(Long chatId) {
        return jdbcTemplate.query(
            "select * from chat where chat_id = ?",
            new ChatMapper(),
            chatId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query("select * from chat", new ChatMapper());
    }
}
