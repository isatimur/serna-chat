package com.timurisachenko.websocketchat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    enum MessageType {
        ENTER, TALK

    }

    private MessageType type; // ENTER or TALK
    private String roomId; // UUID of room
    private String from; // From whom message has been sent
    private String message; // text

}
