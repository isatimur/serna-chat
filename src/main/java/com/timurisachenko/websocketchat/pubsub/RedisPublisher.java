package com.timurisachenko.websocketchat.pubsub;

import com.timurisachenko.websocketchat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic channelTopic, ChatMessage chatMessage){
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }
}
