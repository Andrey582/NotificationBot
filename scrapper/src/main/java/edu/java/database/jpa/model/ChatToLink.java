package edu.java.database.jpa.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chat_to_link")
public class ChatToLink {

    @EmbeddedId
    private ChatToLinkKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("linkId")
    @JoinColumn(name = "link_id")
    private Link link;

    private String name;

    public ChatToLink(Chat chat, Link link, String name) {
        this.chat = chat;
        this.link = link;
        this.id = new ChatToLinkKey(chat.getId(), link.getId());
        this.name = name;
    }

    public ChatToLink() {
    }
}
