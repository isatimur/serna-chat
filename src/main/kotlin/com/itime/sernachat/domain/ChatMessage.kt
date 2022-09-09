package com.itime.sernachat.domain

import java.time.LocalDateTime

class ChatMessage private constructor(
    var type: MessageType?,
    var roomId: String?,
    var from: String?,
    var message: String?,
    var userCount: Long? = 0
) {

    enum class MessageType {
        ENTER, TALK, QUIT
    }

    data class Builder(
        var type: MessageType? = null,
        var roomId: String? = null,
        var from: String? = null,
        var message: String? = null,
        var userCount: Long? = 0
    ) {

        fun type(type: MessageType) = apply { this.type = type }
        fun roomId(roomId: String) = apply { this.roomId = roomId }
        fun from(from: String) = apply { this.from = from }
        fun message(message: String) = apply { this.message = message }
        fun build() = ChatMessage(type, roomId, from, message, userCount)
    }
}