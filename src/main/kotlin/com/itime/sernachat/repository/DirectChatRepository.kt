package com.itime.sernachat.repository

import com.itime.sernachat.domain.DirectChat
import com.itime.sernachat.domain.GroupChat
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DirectChatRepository : MongoRepository<DirectChat, String> {
    fun findBySenderIdAndRecipientId(senderId: String?, recipientId: String?): Optional<DirectChat?>?
}

@Repository
interface GroupChatRepository : MongoRepository<GroupChat, String> {
}