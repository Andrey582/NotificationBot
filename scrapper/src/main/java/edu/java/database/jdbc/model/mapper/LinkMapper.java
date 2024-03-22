package edu.java.database.jdbc.model.mapper;

import edu.java.database.jdbc.model.Link;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import org.springframework.jdbc.core.RowMapper;

public class LinkMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            return new Link(
                resultSet.getLong("id"),
                new URI(resultSet.getString("link_url")),
                resultSet.getObject("last_check_time", OffsetDateTime.class)
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
