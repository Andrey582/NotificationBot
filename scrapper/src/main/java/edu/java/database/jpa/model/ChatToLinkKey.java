package edu.java.database.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ChatToLinkKey implements Serializable {

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "link_id")
    private Long linkId;

    public ChatToLinkKey(Long chatId, Long linkId) {
        this.chatId = chatId;
        this.linkId = linkId;
    }

    public ChatToLinkKey() {
    }
}
