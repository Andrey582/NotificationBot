package edu.java.scrapper.repository;

import edu.java.database.jdbc.JdbcLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.net.URISyntaxException;
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
    public void findLinkByUrlTest() throws URISyntaxException {
        assertThat(jdbcLinkRepository.findLinkByUrl(new URI("http://test.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jdbcLinkRepository.findAll().size()).isEqualTo(4);
    }
}