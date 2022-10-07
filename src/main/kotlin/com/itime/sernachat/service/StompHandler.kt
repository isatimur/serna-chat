package com.itime.sernachat.service

//import com.itime.sernachat.repository.1]ChatRoomRepository
import org.slf4j.Logger
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class StompHandler(
    val logger: Logger,
    val jwtTokenProvider: JwtTokenProvider,
//    val chatRoomRepository: ChatRoomRepository,
    val chatService: ChatService
) : ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
//        val accessor = StompHeaderAccessor.wrap(message)
//        if (StompCommand.CONNECT == accessor.command) {

//            val jwtToken = accessor.getFirstNativeHeader("token")!!
//            logger.info("CONNECT {}", jwtToken)
//            jwtTokenProvider.validateToken(jwtToken)

//        } else if (StompCommand.SUBSCRIBE == accessor.command) {

//            val roomId = "123"
//            val sessionId = message.headers["simpSessionId"] as String
//            chatRoomRepository.setUserEnterInfo(sessionId, roomId)
//            chatRoomRepository.plusUserCount(roomId)
//            val name = Optional.ofNullable(message.headers["simpUser"] as Principal?).map { obj: Principal -> obj.name }
//                .orElse("UnknownUser")
//            chatService.sendChatMessage(
//                CommonMessage.type(CommonMessage.MessageType.ENTER).chatId(roomId).sender(name).build()
//            )
//            logger.info("SUBSCRIBED {}, {}", name, roomId)
//        } else if (StompCommand.DISCONNECT == accessor.command) {

//            val sessionId = message.headers["simpSessionId"] as String
//            val roomId = chatRoomRepository.getUserEnterRoomId(sessionId)!!
//            chatRoomRepository.minusUserCount(roomId)
//            val name = Optional.ofNullable(message.headers["simpUser"] as Principal?).map { obj: Principal -> obj.name }
//                .orElse("UnknownUser")
//            chatService.sendChatMessage(
//                CommonMessage.builder().type(CommonMessage.MessageType.QUIT).roomId(roomId).from(name).build()
//            )
//            chatRoomRepository.removeUserEnterInfo(sessionId)
//            logger.info("DISCONNECTED {}, {}", sessionId, roomId)
//        }
        return message
    }
}