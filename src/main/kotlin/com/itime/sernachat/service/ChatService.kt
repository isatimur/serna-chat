package com.itime.sernachat.service

import com.itime.sernachat.domain.Chat
import com.itime.sernachat.domain.CommonMessage
import java.util.*

interface ChatService {
    fun sendChatMessage(chatMessage: CommonMessage)
    fun getOrCreateChat(chat: Chat): Optional<Chat>?
    fun getChatById(id: String): Optional<Chat>
    fun getChatById(senderId: String, recepientId: String, boolean: Boolean): Optional<Chat>
    fun getAllChats(): List<Chat>
    fun getAllMessages(): List<CommonMessage>
    fun findChatMessages(senderId: String, recepientId: String): List<CommonMessage>
    fun save(chats: MutableList<Chat>): MutableList<Chat>
    fun saveMessages(messages: MutableList<CommonMessage>): MutableList<CommonMessage>
    fun countNewMessages(senderId: String?, recipientId: String?): Long?
    fun save(commonMessage: CommonMessage): CommonMessage
}
