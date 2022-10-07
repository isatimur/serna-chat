package com.itime.sernachat.controller

import com.itime.sernachat.domain.Chat
import com.itime.sernachat.domain.CommonMessage
import com.itime.sernachat.domain.ImageMessage
import com.itime.sernachat.domain.TextMessage
//import com.itime.sernachat.repository.ChatRoomRepository
import com.itime.sernachat.service.ChatService
import com.itime.sernachat.service.JwtTokenProvider
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin("http://localhost:3000")
@Controller
class ChatController(
    val jwtTokenProvider: JwtTokenProvider,
    val chatService: ChatService,
    val messagingTemplate: SimpMessagingTemplate
) {

    @MessageMapping("/chat")
    fun processMessage(@Payload commonMessage: CommonMessage) {
        val chatId: Optional<Chat> =
            chatService.getChatById(commonMessage.sender!!.id!!, commonMessage.chatId!!, true)
        chatId.map {
            commonMessage.chatId = it.id
        }
        val saved: CommonMessage = chatService.save(commonMessage)
    }

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    fun receivePublicMessage(@Payload message: CommonMessage): CommonMessage {
        return message
    }

    @MessageMapping("/private-message")
    @SendTo("/chatroom/public")
    fun receivePrivateMessage(@Payload message: CommonMessage): CommonMessage {
        if (message is TextMessage) {
            val text = message as TextMessage
            messagingTemplate.convertAndSendToUser(text.sender?.id!!, "/private", text.content)
        } else if (message is ImageMessage) {
            val image = message as ImageMessage
            messagingTemplate.convertAndSendToUser(image.sender?.id!!, "/private", image.imageLink)
        }
        return message
    }

    @GetMapping("/v1/api/chats")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    @ResponseBody
    fun getAllChats(): List<Chat> {
        return chatService.getAllChats()
    }

    @GetMapping("/v1/api/messages")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    @ResponseBody
    fun getAllMessages(): List<CommonMessage> {
        return chatService.getAllMessages()
    }

    @PutMapping("/v1/api/chats")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    @ResponseBody
    fun getOrCreateChat(@RequestBody chat: Chat): Optional<Chat>? {
        return chatService.getOrCreateChat(chat)
    }

    @GetMapping("/v1/api/chats/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    @ResponseBody
    fun getChatById(@PathVariable("id") id: String): Optional<Chat> {
        return chatService.getChatById(id)
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    fun countNewMessages(
        @PathVariable senderId: String?,
        @PathVariable recipientId: String?
    ): ResponseEntity<Long?>? {
        return ResponseEntity
            .ok(chatService.countNewMessages(senderId, recipientId))
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    fun findChatMessages(
        @PathVariable senderId: String?,
        @PathVariable recipientId: String?
    ): ResponseEntity<*>? {
        return ResponseEntity
            .ok(chatService.findChatMessages(senderId!!, recipientId!!))
    }

}