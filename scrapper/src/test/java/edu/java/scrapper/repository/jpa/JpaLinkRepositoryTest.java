package edu.java.scrapper.repository.jpa;

import edu.java.database.jpa.JpaLinkRepository;
import edu.java.database.jpa.model.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.OffsetDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationTest implements JpaSetProperty {

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() throws URISyntaxException {
        assertThat(jpaLinkRepository.save(new Link(new URI("http://anothertest.com"), OffsetDateTime.now()))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() throws URISyntaxException {
        boolean before = jpaLinkRepository.existsLinkByLinkUrl(new URI("http://deletetest.com"));
        jpaLinkRepository.deleteByLinkUrl(new URI("http://deletetest.com"));
        boolean after = jpaLinkRepository.existsLinkByLinkUrl(new URI("http://deletetest.com"));

        assertThat(before).isNotEqualTo(after);
    }

    @Test
    @Transactional
    @Rollback
    public void updateLastCheckTest() throws URISyntaxException {

        assertThat(jpaLinkRepository.updateLastCheckByLinkUrl(new URI("http://deletetest.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkByUrlTest() throws URISyntaxException {
        assertThat(jpaLinkRepository.findLinkByLinkUrl(new URI("http://test.com"))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findByTimeTest() {
        assertThat(jpaLinkRepository.findByLastCheckTimeIsAfterDuration(Duration.ofMillis(1)).isEmpty()).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jpaLinkRepository.findAll().size()).isEqualTo(4);
    }
}
