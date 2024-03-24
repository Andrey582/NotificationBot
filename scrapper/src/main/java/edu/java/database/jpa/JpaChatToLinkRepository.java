package edu.java.database.jpa;

import edu.java.database.jpa.model.ChatToLink;
import java.net.URI;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatToLinkRepository extends CrudRepository<ChatToLink, Long> {

    int deleteByChatIdAndLinkId(Long chatId, Long linkId);

    int deleteByChatIdAndName(Long chatId, String name);

    List<ChatToLink> findAllByChatId(Long chatId);

    List<ChatToLink> findAllByLinkId(Long linkId);

    ChatToLink findByChatIdAndLinkLinkUrl(Long chatId, URI linkUrl);

    ChatToLink findAllByChatIdAndAndName(Long chatId, String name);

    List<ChatToLink> findAll();
}
