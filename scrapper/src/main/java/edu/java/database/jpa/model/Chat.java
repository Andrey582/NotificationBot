package edu.java.database.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chat")
public class Chat {

    @Id
    private Long id;

    @OneToMany(mappedBy = "chat")
    private List<ChatToLink> chatToLinks;

    public Chat(Long id) {
        this.id = id;
    }

    public Chat() {
    }
}
