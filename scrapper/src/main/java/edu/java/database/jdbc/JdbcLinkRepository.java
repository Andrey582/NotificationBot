package edu.java.database.jdbc;

import edu.java.database.jdbc.model.Link;
import edu.java.database.jdbc.model.mapper.LinkMapper;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLinkRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Link add(URI linkUrl) {
        return jdbcTemplate.query(
            "insert into link(link_url, last_check_time) values (?, ?) on conflict do nothing returning *",
            new LinkMapper(),
            linkUrl.toString(),
            OffsetDateTime.now())
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Link remove(URI linkUrl) {
        return jdbcTemplate.query(
            "delete from link where link_url = ? returning *",
            new LinkMapper(),
            linkUrl.toString())
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Link findLinkByUrl(URI linkUrl) {
        return jdbcTemplate.query(
            "select * from link where link_url = ?",
            new LinkMapper(),
            linkUrl.toString())
            .stream()
            .findFirst()
            .orElse(null);
    }

    public Link updateLastCheck(URI linkUrl) {
        return jdbcTemplate.query(
            "update link set last_check_time = ? where link_url = ? returning *",
            new LinkMapper(),
            OffsetDateTime.now(),
            linkUrl.toString())
            .stream()
            .findFirst()
            .orElse(null);
    }

    public List<Link> findAll() {
        return jdbcTemplate.query("select * from link", new LinkMapper());
    }

    public List<Link> findByTime(Duration duration) {
        return jdbcTemplate.query(
            "select * from link where last_check_time < ?",
            new LinkMapper(),
            OffsetDateTime.now().minus(duration));
    }
}
