package com.timurisachenko.websocketchat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK, QUIT
    }
    public ChatMessage() {}
    @Builder
    public ChatMessage(MessageType type, String roomId, String from, String message, long userCount) {
        this.type = type;
        this.roomId = roomId;
        this.from = from;
        this.message = message;
        this.userCount = userCount;
    }

    private MessageType type;
    private String roomId;
    private String from;
    private String message;
    private long userCount;
}