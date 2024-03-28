package edu.java.scrapper.repository.jooq;

import edu.java.database.jooq.repository.JooqChatRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JooqChatRepositoryTest extends IntegrationTest implements JooqSetProperty {

    @Autowired
    private JooqChatRepository jooqChatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        assertThat(jooqChatRepository.add(1234L)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        assertThat(jooqChatRepository.remove(345L)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jooqChatRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @Transactional
    @Rollback
    public void findByChatTest() {
        assertThat(jooqChatRepository.findChatById(123L)).isNotNull();
    }

}
