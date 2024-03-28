package edu.java.database.jpa;

import edu.java.database.jpa.model.Chat;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends CrudRepository<Chat, Long> {

    Chat findChatById(Long id);

    List<Chat> findAll();
}
