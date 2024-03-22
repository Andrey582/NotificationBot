package edu.java.database.jdbc.model;

import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    Long id;
    URI linkUrl;
    OffsetDateTime lastCheckTime;
}
