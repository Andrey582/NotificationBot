package edu.java.database.repository.jdbc;

import edu.java.database.model.ChatToLink;
import edu.java.database.model.mapper.ChatToLinkMapper;
import edu.java.database.repository.ChatToLinkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@SuppressWarnings("MultipleStringLiterals")
@Repository
public class JdbcChatToLinkRepository implements ChatToLinkRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean add(Long chatId, Long linkId, String name) {
        return jdbcTemplate.update(
            "insert into chat_to_link (chat_id, link_id, name) values (?, ?, ?) on conflict do nothing ",
            chatId,
            linkId,
            name
        ) > 0;
    }

    @Override
    public boolean remove(Long chatId, Long linkId) {
        return jdbcTemplate.update(
            "delete from chat_to_link where chat_id = ? and link_id = ?",
            chatId,
            linkId
        ) > 0;
    }

    @Override
    public boolean remove(Long chatId, String name) {
        return jdbcTemplate.update(
            "delete from chat_to_link where chat_id = ? and name = ?",
            chatId,
            name
        ) > 0;
    }

    @Override
    public List<ChatToLink> findAllLinkByChat(Long chatId) {
        return jdbcTemplate.query(
            "select chat.id as cid, chat.chat_id, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id "
                + "where chat_to_link.chat_id = ?",
            new ChatToLinkMapper(),
            chatId
        );
    }

    @Override
    public List<ChatToLink> findAllLinksByName(Long chatId, String name) {
        return jdbcTemplate.query(
            "select chat.id as cid, chat.chat_id, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id "
                + "where chat_to_link.chat_id = ? and name = ?",
            new ChatToLinkMapper(),
            chatId,
            name
        );
    }

    @Override
    public List<ChatToLink> findAllChatByLink(Long linkId) {
        return jdbcTemplate.query(
            "select chat.id as cid, chat.chat_id, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id "
                + "where chat_to_link.link_id = ?",
            new ChatToLinkMapper(),
            linkId
        );
    }

    @Override
    public List<ChatToLink> findAll() {
        return jdbcTemplate.query(
            "select chat.id as cid, chat.chat_id, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id",
            new ChatToLinkMapper()
        );
    }

}
