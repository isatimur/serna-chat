package com.itime.sernachat.service

import com.itime.sernachat.domain.ChatMessage
import com.itime.sernachat.repository.ChatRoomRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class DefaultChatService(
    val channelTopic: ChannelTopic,
    val redisTemplate: RedisTemplate<*, *>,
    val chatRoomRepository: ChatRoomRepository
) : ChatService {
    override fun getRoomId(destination: String): String {
        val lastIndex = destination.lastIndexOf('/')
        return if (lastIndex != -1) destination.substring(lastIndex + 1) else ""
    }

    override fun sendChatMessage(chatMessage: ChatMessage) {
        chatMessage.userCount = chatRoomRepository.getUserCount(chatMessage.roomId!!)
        if (ChatMessage.MessageType.ENTER == chatMessage.type) {
            chatMessage.message = chatMessage.from + "— Вы вошли в комнату"
            chatMessage.from = "[уведомление]"
        } else if (ChatMessage.MessageType.QUIT == chatMessage.type) {
            chatMessage.message = chatMessage.from + "— Вы вошли в комнату"
            chatMessage.from = "[уведомление]"
        }
        redisTemplate.convertAndSend(channelTopic.topic, chatMessage)
    }
}