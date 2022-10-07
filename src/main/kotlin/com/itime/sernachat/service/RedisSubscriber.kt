package com.itime.sernachat.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.itime.sernachat.domain.CommonMessage
import org.slf4j.Logger
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service


@Service
class RedisSubscriber(
    val logger: Logger,
    val objectMapper: ObjectMapper,
    val messagingTemplate: SimpMessageSendingOperations
) {
    fun sendMessage(publishMessage: String?) {
        try {
            val message: CommonMessage = objectMapper.readValue(publishMessage, CommonMessage::class.java)
            messagingTemplate.convertAndSend("/topic/chat/room/" + message.chatId, message)
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }
}
