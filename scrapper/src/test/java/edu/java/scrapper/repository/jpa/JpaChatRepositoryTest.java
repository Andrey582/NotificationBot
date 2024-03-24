package edu.java.scrapper.repository.jpa;

import edu.java.database.jpa.JpaChatRepository;
import edu.java.database.jpa.model.Chat;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaChatRepositoryTest extends IntegrationTest implements JpaSetProperty {

    @Autowired
    JpaChatRepository jpaChatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        assertThat(jpaChatRepository.save(new Chat(123L)).getId()).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        boolean before = jpaChatRepository.existsById(345L);
        jpaChatRepository.deleteById(345L);
        boolean after = jpaChatRepository.existsById(435L);

        assertThat(before).isNotEqualTo(after);
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jpaChatRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    public void findByChatTest() {
        assertThat(jpaChatRepository.findChatById(123L)).isNotNull();
    }


}
