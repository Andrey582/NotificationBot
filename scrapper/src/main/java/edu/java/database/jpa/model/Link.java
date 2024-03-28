package edu.java.database.jpa.model;

import edu.java.database.jpa.UriConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_url")
    @Convert(converter = UriConverter.class)
    private URI linkUrl;

    @Column(name = "last_check_time")
    private OffsetDateTime lastCheckTime;

    @OneToMany(mappedBy = "link")
    private List<ChatToLink> chatToLinks;

    public Link(URI linkUrl, OffsetDateTime lastCheckTime) {
        this.linkUrl = linkUrl;
        this.lastCheckTime = lastCheckTime;
    }

    public Link() {
    }
}
