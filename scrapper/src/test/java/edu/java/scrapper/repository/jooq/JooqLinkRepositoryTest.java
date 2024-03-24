package edu.java.scrapper.repository.jooq;

import edu.java.database.jooq.repository.JooqLinkRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationTest implements JooqSetProperty {

    @Autowired
    JooqLinkRepository jooqLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() throws URISyntaxException {
        assertThat(jooqLinkRepository.add(new URI("http://anothertest.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() throws URISyntaxException {
        assertThat(jooqLinkRepository.remove(new URI("http://deletetest.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void updateLastCheckTest() throws URISyntaxException {
        assertThat(jooqLinkRepository.updateLastCheck(new URI("http://test.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByUrlTest() throws URISyntaxException {
        assertThat(jooqLinkRepository.findLinkByUrl(new URI("http://test.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findByTimeTest() {
        assertThat(jooqLinkRepository.findByTime(Duration.ofMillis(1)).isEmpty()).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jooqLinkRepository.findAll().size()).isEqualTo(4);
    }
}
