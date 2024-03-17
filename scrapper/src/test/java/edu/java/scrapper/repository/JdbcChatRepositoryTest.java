package edu.java.scrapper.repository;

import edu.java.database.jdbc.JdbcChatRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationTest {

    @Autowired
    JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        assertThat(jdbcChatRepository.add(123L)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        assertThat(jdbcChatRepository.remove(345L)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        assertThat(jdbcChatRepository.findAll().size()).isEqualTo(3);
    }
}
