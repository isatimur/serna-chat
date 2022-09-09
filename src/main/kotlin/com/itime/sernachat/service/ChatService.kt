package com.itime.sernachat.service

import com.itime.sernachat.domain.ChatMessage

interface ChatService {
    //    fun handleNewMessageEvent(senderId: UUID, newMessageEvent: NewMessageEvent): Mono<Void>
//    fun markPreviousMessagesAsRead(messageId: UUID): Mono<Void>
//    fun sendEventToUserId(userId: UUID, webSocketEvent: WebSocketEvent): Mono<Void>
//    fun sendMessage(message: CommonMessage): Mono<Void>
//    fun broadcastMessage(commonMessage: CommonMessage): Mono<Void>
    fun getRoomId(destination: String): String?
    fun sendChatMessage(chatMessage: ChatMessage)
}
