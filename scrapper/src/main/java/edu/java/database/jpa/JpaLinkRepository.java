package edu.java.database.jpa;

import edu.java.database.jpa.model.Link;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaLinkRepository extends CrudRepository<Link, Long> {

    Boolean existsLinkByLinkUrl(URI link);

    void deleteByLinkUrl(URI link);

    @Modifying
    @Query(value = "update link set last_check_time = now() where link_url = ?1", nativeQuery = true)
    int updateLastCheckByStringLink(String link);

    @Transactional
    default int updateLastCheckByLinkUrl(URI link) {
        return updateLastCheckByStringLink(link.toString());
    }

    @Query("select l from Link l where l.lastCheckTime < ?1")
    List<Link> findByDateTime(OffsetDateTime offsetDateTime);

    default List<Link> findByLastCheckTimeIsAfterDuration(Duration duration) {
        return findByDateTime(OffsetDateTime.now().minus(duration));
    }

    Link findLinkByLinkUrl(URI link);

    List<Link> findAll();


}
