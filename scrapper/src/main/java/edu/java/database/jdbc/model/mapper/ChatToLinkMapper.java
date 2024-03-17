package edu.java.database.jdbc.model.mapper;

import edu.java.database.jdbc.model.Chat;
import edu.java.database.jdbc.model.ChatToLink;
import edu.java.database.jdbc.model.Link;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import org.springframework.jdbc.core.RowMapper;

public class ChatToLinkMapper implements RowMapper<ChatToLink> {
    @Override
    public ChatToLink mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            return new ChatToLink(
                new Chat(
                    resultSet.getLong("cid")
                ),
                new Link(
                    resultSet.getLong("lid"),
                    new URI(resultSet.getString("link_url")),
                    resultSet.getObject("last_check_time", OffsetDateTime.class)
                ),
                resultSet.getString("name")
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
