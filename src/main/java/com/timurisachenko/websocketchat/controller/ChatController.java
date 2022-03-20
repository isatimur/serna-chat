package com.timurisachenko.websocketchat.controller;

import com.timurisachenko.websocketchat.model.ChatMessage;
import com.timurisachenko.websocketchat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChannelTopic channelTopic;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserFromToken(token);
        message.setFrom(nickname);
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setFrom("[notification]");
            message.setMessage(nickname + " Thx for joining");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
