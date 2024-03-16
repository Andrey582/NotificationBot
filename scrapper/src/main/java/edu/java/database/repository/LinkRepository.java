package edu.java.database.repository;

import edu.java.database.model.Link;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface LinkRepository {
    @Transactional
    Link add(URI linkUrl);

    @Transactional
    Link remove(URI linkUrl);

    @Transactional
    Link updateLastCheck(URI linkUrl);

    @Transactional(readOnly = true)
    Link findLinkByUrl(URI linkUrl);

    @Transactional(readOnly = true)
    List<Link> findAll();

    @Transactional(readOnly = true)
    List<Link> findByTime(Duration duration);
}
