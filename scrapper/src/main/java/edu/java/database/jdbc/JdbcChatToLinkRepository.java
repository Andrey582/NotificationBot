package edu.java.database.jdbc;

import edu.java.database.jdbc.model.ChatToLink;
import edu.java.database.jdbc.model.mapper.ChatToLinkMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@SuppressWarnings("MultipleStringLiterals")
@Repository
public class JdbcChatToLinkRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean add(Long chatId, Long linkId, String name) {
        return jdbcTemplate.update(
            "insert into chat_to_link (chat_id, link_id, name) values (?, ?, ?) on conflict do nothing ",
            chatId,
            linkId,
            name
        ) > 0;
    }

    public boolean remove(Long chatId, Long linkId) {
        return jdbcTemplate.update(
            "delete from chat_to_link where chat_id = ? and link_id = ?",
            chatId,
            linkId
        ) > 0;
    }

    public boolean remove(Long chatId, String name) {
        return jdbcTemplate.update(
            "delete from chat_to_link where chat_id = ? and name = ?",
            chatId,
            name
        ) > 0;
    }

    public List<ChatToLink> findAllLinkByChat(Long chatId) {
        return jdbcTemplate.query(
            "select chat.id as cid, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id "
                + "where chat_to_link.chat_id = ?",
            new ChatToLinkMapper(),
            chatId
        );
    }

    public List<ChatToLink> findAllLinksByName(Long chatId, String name) {
        return jdbcTemplate.query(
            "select chat.id as cid, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id "
                + "where chat_to_link.chat_id = ? and name = ?",
            new ChatToLinkMapper(),
            chatId,
            name
        );
    }

    public List<ChatToLink> findAllChatByLink(Long linkId) {
        return jdbcTemplate.query(
            "select chat.id as cid, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id "
                + "where chat_to_link.link_id = ?",
            new ChatToLinkMapper(),
            linkId
        );
    }

    public List<ChatToLink> findAll() {
        return jdbcTemplate.query(
            "select chat.id as cid, "
                + "link.id as lid, link.link_url, link.last_check_time, name from chat_to_link "
                + "join chat on chat.id = chat_to_link.chat_id "
                + "join link on link.id = chat_to_link.link_id",
            new ChatToLinkMapper()
        );
    }

}
