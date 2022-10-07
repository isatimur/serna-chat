package com.itime.sernachat.service

//import com.itime.sernachat.repository.ChatRoomRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.itime.sernachat.domain.Chat
import com.itime.sernachat.domain.CommonMessage
import com.itime.sernachat.repository.DirectChatRepository
import com.itime.sernachat.repository.GroupChatRepository
import org.slf4j.Logger
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultChatService(
    val logger: Logger,
    val objectMapper: ObjectMapper,
    val channelTopic: ChannelTopic,
    val redisTemplate: RedisTemplate<*, *>,
    val directChatRepository: DirectChatRepository,
    val groupChatRepository: GroupChatRepository
) : ChatService {
    override fun sendChatMessage(chatMessage: CommonMessage) {
        TODO("Not yet implemented")
    }

    override fun getOrCreateChat(chat: Chat): Optional<Chat>? {
        TODO("Not yet implemented")
    }

    override fun getChatById(id: String): Optional<Chat> {
        TODO("Not yet implemented")
    }

    override fun getChatById(senderId: String, receipterId: String, boolean: Boolean): Optional<Chat> {
        TODO("Not yet implemented")
    }

    override fun getAllChats(): List<Chat> {
        TODO("Not yet implemented")
    }

    override fun getAllMessages(): List<CommonMessage> {
        TODO("Not yet implemented")
    }

    override fun findChatMessages(senderId: String, recepientId: String): List<CommonMessage> {
        TODO("Not yet implemented")
    }

    override fun save(chats: MutableList<Chat>): MutableList<Chat> {
        TODO("Not yet implemented")
    }

    override fun save(commonMessage: CommonMessage): CommonMessage {
        TODO("Not yet implemented")
    }

    override fun saveMessages(messages: MutableList<CommonMessage>): MutableList<CommonMessage> {
        TODO("Not yet implemented")
    }

    override fun countNewMessages(senderId: String?, recipientId: String?): Long? {
        TODO("Not yet implemented")
    }

}