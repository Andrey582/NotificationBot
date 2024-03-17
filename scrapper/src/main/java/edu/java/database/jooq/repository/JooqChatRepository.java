package edu.java.database.jooq.repository;

import edu.java.database.jooq.tables.pojos.Chat;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

import static edu.java.database.jooq.tables.Chat.CHAT;

@Repository
public class JooqChatRepository {

    @Autowired
    DSLContext dslContext;

    public Chat add(Long chatId) {
        return dslContext
            .insertInto(CHAT, CHAT.ID)
            .values(chatId)
            .onConflictDoNothing()
            .returning()
            .fetchOneInto(Chat.class);
    }

    public Chat remove(Long chatId) {
        return dslContext
            .deleteFrom(CHAT)
            .where(CHAT.ID.eq(chatId))
            .returning()
            .fetchOneInto(Chat.class);
    }

    public Chat findChatById(Long chatId) {
        return dslContext
            .select(CHAT)
            .from(CHAT)
            .where(CHAT.ID.eq(chatId))
            .fetchOneInto(Chat.class);

    }

    public List<Chat> findAll() {
        return dslContext
            .select(CHAT)
            .from(CHAT)
            .fetchInto(Chat.class);
    }
}
