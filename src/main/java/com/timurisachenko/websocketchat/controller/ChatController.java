package com.timurisachenko.websocketchat.controller;

import com.timurisachenko.websocketchat.model.ChatMessage;
import com.timurisachenko.websocketchat.pubsub.RedisPublisher;
import com.timurisachenko.websocketchat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher publisher;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getFrom() + " Thx for joining");
        }
        publisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }


}
