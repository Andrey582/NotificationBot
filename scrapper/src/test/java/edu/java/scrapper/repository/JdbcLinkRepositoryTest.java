package edu.java.scrapper.repository;

import edu.java.database.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationTest {

    @Autowired
    JdbcLinkRepository jdbcLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() throws URISyntaxException {
        assertThat(jdbcLinkRepository.add(new URI("http://anothertest.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() throws URISyntaxException {
        assertThat(jdbcLinkRepository.remove(new URI("http://deletetest.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void updateLastCheckTest() throws URISyntaxException {
        assertThat(jdbcLinkRepository.updateLastCheck(new URI("http://test.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByUrlTest() throws URISyntaxException {
        assertThat(jdbcLinkRepository.findLinkByUrl(new URI("http://test.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findByTimeTest() {
        assertThat(jdbcLinkRepository.findByTime(Duration.ofMillis(1)).isEmpty()).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jdbcLinkRepository.findAll().size()).isEqualTo(4);
    }
}
