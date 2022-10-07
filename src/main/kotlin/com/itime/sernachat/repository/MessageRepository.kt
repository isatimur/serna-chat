package com.itime.sernachat.repository

import com.itime.sernachat.domain.CommonMessage
import com.itime.sernachat.domain.CommonMessageDto
import com.itime.sernachat.domain.MessageStatus
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : MongoRepository<CommonMessage, String> {
    fun countBySenderIdAndRecipientIdAndStatus(
        senderId: String?, recipientId: String?, seen: Boolean?
    ): Long

    fun findByChatId(chatId: String?): List<CommonMessage?>?
}

