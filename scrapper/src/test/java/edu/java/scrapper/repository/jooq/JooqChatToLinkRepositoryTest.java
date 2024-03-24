package edu.java.scrapper.repository.jooq;

import edu.java.database.jooq.repository.JooqChatRepository;
import edu.java.database.jooq.repository.JooqChatToLinkRepository;
import edu.java.database.jooq.repository.JooqLinkRepository;
import edu.java.database.jooq.tables.pojos.Chat;
import edu.java.database.jooq.tables.pojos.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JooqChatToLinkRepositoryTest extends IntegrationTest implements JooqSetProperty {

    @Autowired
    JooqChatToLinkRepository jooqChatToLinkRepository;
    @Autowired
    JooqChatRepository jooqChatRepository;
    @Autowired
    JooqLinkRepository jooqLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() throws URISyntaxException {
        Chat chat = jooqChatRepository.findChatById(234L);
        Link link = jooqLinkRepository.findLinkByUrl(new URI("http://test.com"));
        assertThat(jooqChatToLinkRepository.add(chat.getId(), link.getId(), null)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void removeByUrlTest() throws URISyntaxException {
        Chat chat = jooqChatRepository.findChatById(123L);
        Link link = jooqLinkRepository.findLinkByUrl(new URI("http://test.com"));
        assertThat(jooqChatToLinkRepository.remove(chat.getId(), link.getId())).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void removeByNameTest() throws URISyntaxException {
        Chat chat = jooqChatRepository.findChatById(123L);
        assertThat(jooqChatToLinkRepository.remove(chat.getId(), "test")).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllLinkByChatIdTest() {
        Chat chat = jooqChatRepository.findChatById(234L);
        assertThat(jooqChatToLinkRepository.findAllLinkByChat(chat.getId()).size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllChatByLinkIdTest() throws URISyntaxException {
        Link link = jooqLinkRepository.findLinkByUrl(new URI("http://test.com"));
        assertThat(jooqChatToLinkRepository.findAllChatByLink(link.getId()).size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void findByNameTest() {
        Chat chat = jooqChatRepository.findChatById(123L);
        assertThat(jooqChatToLinkRepository.findAllLinksByName(chat.getId(), "test")).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jooqChatToLinkRepository.findAll().size()).isEqualTo(5);
    }
}
