package edu.java.database.jooq.repository;

import edu.java.database.jooq.tables.pojos.Link;
import edu.java.database.jooq.tables.records.ChatRecord;
import edu.java.database.jooq.tables.records.ChatToLinkRecord;
import edu.java.database.jooq.tables.records.LinkRecord;
import jakarta.annotation.Nullable;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.database.jooq.tables.Chat.CHAT;
import static edu.java.database.jooq.tables.ChatToLink.CHAT_TO_LINK;
import static edu.java.database.jooq.tables.Link.LINK;

@Repository
public class JooqChatToLinkRepository {

    @Autowired
    DSLContext dslContext;

    public boolean add(Long chatId, Long linkId, @Nullable String name) {
        return dslContext
            .insertInto(CHAT_TO_LINK, CHAT_TO_LINK.CHAT_ID, CHAT_TO_LINK.LINK_ID, CHAT_TO_LINK.NAME)
            .values(chatId, linkId, name)
            .onConflictDoNothing()
            .returning()
            .stream()
            .findAny()
            .isPresent();
    }

    public boolean remove(Long chatId, Long linkId) {
        return dslContext
            .deleteFrom(CHAT_TO_LINK)
            .where(CHAT_TO_LINK.CHAT_ID.eq(chatId).and(CHAT_TO_LINK.LINK_ID.eq(linkId)))
            .returning()
            .stream()
            .findAny()
            .isPresent();
    }

    public boolean remove(Long chatId, String name) {
        return dslContext
            .deleteFrom(CHAT_TO_LINK)
            .where(CHAT_TO_LINK.CHAT_ID.eq(chatId).and(CHAT_TO_LINK.NAME.eq(name)))
            .returning()
            .stream()
            .findAny()
            .isPresent();
    }

    public List<Record2<LinkRecord, ChatToLinkRecord>> findAllLinkByChat(Long chatId) {
        return dslContext
            .select(LINK, CHAT_TO_LINK)
            .from(CHAT_TO_LINK)
            .join(LINK)
            .on(CHAT_TO_LINK.LINK_ID.eq(LINK.ID))
            .where(CHAT_TO_LINK.CHAT_ID.eq(chatId))
            .fetch();
    }

    public List<Link> findAllLinksByName(Long chatId, String name) {
        return dslContext
            .select(LINK)
            .from(CHAT_TO_LINK)
            .join(LINK)
            .on(CHAT_TO_LINK.LINK_ID.eq(LINK.ID))
            .where(CHAT_TO_LINK.CHAT_ID.eq(chatId).and(CHAT_TO_LINK.NAME.eq(name)))
            .fetchInto(Link.class);
    }

    public List<Record2<ChatRecord, ChatToLinkRecord>> findAllChatByLink(Long linkId) {
        return dslContext
            .select(CHAT, CHAT_TO_LINK)
            .from(CHAT_TO_LINK)
            .join(CHAT)
            .on(CHAT_TO_LINK.CHAT_ID.eq(CHAT.ID))
            .where(CHAT_TO_LINK.LINK_ID.eq(linkId))
            .fetch();
    }

    public List<Record2<ChatRecord, LinkRecord>> findAll() {
        return dslContext
            .select(CHAT, LINK)
            .from(CHAT_TO_LINK)
            .join(CHAT)
            .on(CHAT.ID.eq(CHAT_TO_LINK.CHAT_ID))
            .join(LINK)
            .on(LINK.ID.eq(CHAT_TO_LINK.LINK_ID)).fetch();
    }
}
