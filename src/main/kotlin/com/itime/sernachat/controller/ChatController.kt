package com.itime.sernachat.controller

import com.itime.sernachat.domain.ChatMessage
import com.itime.sernachat.repository.ChatRoomRepository
import com.itime.sernachat.service.ChatService
import com.itime.sernachat.service.JwtTokenProvider
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller


@Controller
class ChatController(
    val jwtTokenProvider: JwtTokenProvider,
    val chatRoomRepository: ChatRoomRepository,
    val chatService: ChatService
) {

    /**
     * websocket "/app/chat/message".
     */
    @MessageMapping("/chat/message")
    fun message(message: ChatMessage, @Header("token") token: String) {
        val nickname: String = jwtTokenProvider.getUserFromToken(token)
        message.from = nickname
        message.userCount = chatRoomRepository.getUserCount(message.roomId!!)
        chatService.sendChatMessage(message)
    }
}