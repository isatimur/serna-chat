package com.itime.sernachat.domain

import java.io.Serializable
import java.util.*

data class ChatRoom(
    var roomId: String? = null,
    var name: String? = null,
    var userCount: Long = 0
) : Serializable {

    companion object {
        private const val serialVersionUID = 6494678977089006639L
        fun create(name: String?): ChatRoom {
            val chatRoom = ChatRoom()
            chatRoom.roomId = UUID.randomUUID().toString()
            chatRoom.name = name
            return chatRoom
        }
    }
}

