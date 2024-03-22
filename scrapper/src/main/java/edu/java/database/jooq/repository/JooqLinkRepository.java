package edu.java.database.jooq.repository;

import edu.java.database.jooq.tables.pojos.Link;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.database.jooq.tables.Link.LINK;

@Repository
public class JooqLinkRepository {

    @Autowired
    DSLContext dslContext;

    public Link add(URI linkUrl) {
        return dslContext
            .insertInto(LINK, LINK.LINK_URL, LINK.LAST_CHECK_TIME)
            .values(linkUrl.toString(), OffsetDateTime.now())
            .onConflictDoNothing()
            .returning()
            .fetchOneInto(Link.class);
    }

    public Link remove(URI linkUrl) {
        return dslContext
            .deleteFrom(LINK)
            .where(LINK.LINK_URL.eq(linkUrl.toString()))
            .returning()
            .fetchOneInto(Link.class);
    }

    public Link updateLastCheck(URI linkUrl) {
        return dslContext
            .update(LINK)
            .set(LINK.LAST_CHECK_TIME, OffsetDateTime.now())
            .where(LINK.LINK_URL.eq(linkUrl.toString()))
            .returning()
            .fetchOneInto(Link.class);
    }

    public Link findLinkByUrl(URI linkUrl) {
        return dslContext
            .select(LINK)
            .from(LINK)
            .where(LINK.LINK_URL.eq(linkUrl.toString()))
            .fetchOneInto(Link.class);
    }

    public List<Link> findAll() {
        return dslContext
            .select(LINK)
            .from(LINK)
            .fetchInto(Link.class);
    }

    public List<Link> findByTime(Duration duration) {
        return dslContext
            .select(LINK)
            .from(LINK)
            .where(LINK.LAST_CHECK_TIME.lt(OffsetDateTime.now().minus(duration)))
            .fetchInto(Link.class);
    }
}
