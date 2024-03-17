package edu.java.database.model.mapper;

import edu.java.database.model.Chat;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ChatMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Chat(
            resultSet.getLong("id")
        );
    }
}
