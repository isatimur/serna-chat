package com.itime.sernachat.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime
import java.util.*

open class CommonMessage(
    var id: String? = UUID.randomUUID().toString(),
    var chatId: String? = null,
    var sender: ChatMember? = null,
    var type: String? = null,
    var messageDate: LocalDateTime = LocalDateTime.now(),
    var seen: Boolean? = false,
    var status: MessageStatus = MessageStatus.RECEIVED,
    var deleted: Boolean? = false
)

enum class MessageType {
    ENTER, MESSAGE, QUIT
}
enum class MessageStatus {
    RECEIVED, DELIVERED
}
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
open class CommonMessageDto(
    @field:JsonProperty("id")
    val messageId: String,
    val chatId: String,
    val sender: ChatMember,
    val type: String,
    @field:JsonSerialize(using = LocalDateTimeSerializer::class)
    @field:JsonDeserialize(using = LocalDateTimeDeserializer::class)
    val messageDate: LocalDateTime,
    var seen: Boolean
)
