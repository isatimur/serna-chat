package com.itime.sernachat.controller

import com.itime.sernachat.domain.Chat
import com.itime.sernachat.domain.ChatNotification
import com.itime.sernachat.domain.CommonMessage
//import com.itime.sernachat.repository.ChatRoomRepository
import com.itime.sernachat.service.ChatService
import com.itime.sernachat.service.JwtTokenProvider
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*

@CrossOrigin("http://localhost:3000")
@Controller
class MessageController(
    val jwtTokenProvider: JwtTokenProvider,
//    val chatRoomRepository: ChatRoomRepository,
    val chatService: ChatService,
    val messagingTemplate: SimpMessagingTemplate
) {

}