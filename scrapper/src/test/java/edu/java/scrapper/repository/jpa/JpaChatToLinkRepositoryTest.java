package edu.java.scrapper.repository.jpa;

import edu.java.database.jpa.model.Chat;
import edu.java.database.jpa.model.ChatToLink;
import edu.java.database.jpa.model.Link;
import edu.java.database.jpa.JpaChatRepository;
import edu.java.database.jpa.JpaChatToLinkRepository;
import edu.java.database.jpa.JpaLinkRepository;
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
public class JpaChatToLinkRepositoryTest extends IntegrationTest implements JpaSetProperty {

    @Autowired
    JpaChatToLinkRepository jpaChatToLinkRepository;
    @Autowired
    JpaChatRepository jpaChatRepository;
    @Autowired
    JpaLinkRepository jpaLinkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() throws URISyntaxException {
        Chat chat = jpaChatRepository.findChatById(234L);
        Link link = jpaLinkRepository.findLinkByLinkUrl(new URI("http://test.com"));
        assertThat(jpaChatToLinkRepository.save(new ChatToLink(chat, link, null))).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeByUrlTest() throws URISyntaxException {
        Chat chat = jpaChatRepository.findChatById(123L);
        Link link = jpaLinkRepository.findLinkByLinkUrl(new URI("http://test.com"));
        assertThat(jpaChatToLinkRepository.deleteByChatIdAndLinkId(chat.getId(), link.getId())).isNotEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void removeByNameTest() {
        Chat chat = jpaChatRepository.findChatById(123L);
        assertThat(jpaChatToLinkRepository.deleteByChatIdAndName(chat.getId(), "test")).isNotEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllLinkByChatIdTest() {
        Chat chat = jpaChatRepository.findChatById(234L);
        assertThat(jpaChatToLinkRepository.findAllByChatId(chat.getId()).size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllChatByLinkIdTest() throws URISyntaxException {
        Link link = jpaLinkRepository.findLinkByLinkUrl(new URI("http://test.com"));
        assertThat(jpaChatToLinkRepository.findAllByLinkId(link.getId()).size()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void findByNameTest() {
        Chat chat = jpaChatRepository.findChatById(123L);
        assertThat(jpaChatToLinkRepository.findAllByChatIdAndAndName(chat.getId(), "test")).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jpaChatToLinkRepository.findAll().size()).isEqualTo(5);
    }
}
