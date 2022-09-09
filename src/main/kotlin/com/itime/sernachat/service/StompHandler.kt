package com.itime.sernachat.service

import com.itime.sernachat.domain.ChatMessage
import com.itime.sernachat.repository.ChatRoomRepository
import org.slf4j.Logger
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import java.security.Principal
import java.util.*

@Component
class StompHandler(
    val logger: Logger,
    val jwtTokenProvider: JwtTokenProvider,
    val chatRoomRepository: ChatRoomRepository,
    val chatService: ChatService
) : ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor = StompHeaderAccessor.wrap(message)
        if (StompCommand.CONNECT == accessor.command) {

            val jwtToken = accessor.getFirstNativeHeader("token")!!
            logger.info("CONNECT {}", jwtToken)
            jwtTokenProvider.validateToken(jwtToken)

        } else if (StompCommand.SUBSCRIBE == accessor.command) {

            val roomId = chatService.getRoomId(
                Optional.ofNullable(message.headers["simpDestination"] as String).orElse("InvalidRoomId")
            )!!
            val sessionId = message.headers["simpSessionId"] as String
            chatRoomRepository.setUserEnterInfo(sessionId, roomId)
            chatRoomRepository.plusUserCount(roomId)
            val name = Optional.ofNullable(message.headers["simpUser"] as Principal?).map { obj: Principal -> obj.name }
                .orElse("UnknownUser")
            chatService.sendChatMessage(
                ChatMessage.Builder().type(ChatMessage.MessageType.ENTER).roomId(roomId).from(name).build()
            )
            logger.info("SUBSCRIBED {}, {}", name, roomId)
        } else if (StompCommand.DISCONNECT == accessor.command) {

            val sessionId = message.headers["simpSessionId"] as String
            val roomId = chatRoomRepository.getUserEnterRoomId(sessionId)!!
            chatRoomRepository.minusUserCount(roomId)
            val name = Optional.ofNullable(message.headers["simpUser"] as Principal?).map { obj: Principal -> obj.name }
                .orElse("UnknownUser")
            chatService.sendChatMessage(
                ChatMessage.Builder().type(ChatMessage.MessageType.QUIT).roomId(roomId).from(name).build()
            )
            chatRoomRepository.removeUserEnterInfo(sessionId)
            logger.info("DISCONNECTED {}, {}", sessionId, roomId)
        }
        return message
    }
}