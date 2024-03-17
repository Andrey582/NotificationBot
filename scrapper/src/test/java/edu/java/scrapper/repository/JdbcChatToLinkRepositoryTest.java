package edu.java.scrapper.repository;

import edu.java.database.jdbc.JdbcChatRepository;
import edu.java.database.jdbc.JdbcChatToLinkRepository;
import edu.java.database.jdbc.JdbcLinkRepository;
import edu.java.database.jdbc.model.Chat;
import edu.java.database.jdbc.model.Link;
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
public class JdbcChatToLinkRepositoryTest extends IntegrationTest {

    @Autowired
    JdbcChatToLinkRepository jdbcChatToLinkRepository;
    @Autowired
    JdbcChatRepository jdbcChatRepository;
    @Autowired
    JdbcLinkRepository jdbcLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() throws URISyntaxException {
        Chat chat = jdbcChatRepository.findChatById(234L);
        Link link = jdbcLinkRepository.findLinkByUrl(new URI("http://test.com"));
        assertThat(jdbcChatToLinkRepository.add(chat.getId(), link.getId(), null)).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() throws URISyntaxException {
        Chat chat = jdbcChatRepository.findChatById(123L);
        Link link = jdbcLinkRepository.findLinkByUrl(new URI("http://test.com"));
        assertThat(jdbcChatToLinkRepository.remove(chat.getId(), link.getId())).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllLinkByChatIdTest() {
        Chat chat = jdbcChatRepository.findChatById(234L);
        assertThat(jdbcChatToLinkRepository.findAllLinkByChat(chat.getId()).size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllChatByLinkIdTest() throws URISyntaxException {
        Link link = jdbcLinkRepository.findLinkByUrl(new URI("http://test.com"));
        assertThat(jdbcChatToLinkRepository.findAllChatByLink(link.getId()).size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jdbcChatToLinkRepository.findAll().size()).isEqualTo(5);
    }
}
