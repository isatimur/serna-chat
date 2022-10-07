package com.itime.sernachat.repository

import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.Resource

//@Repository
//class ChatRoomRepository {
//    @Resource(name = "redisTemplate")
//    private val hashOpsChatRoom: HashOperations<String, String, ChatRoom>? = null
//
//    @Resource(name = "redisTemplate")
//    private val hashOpsEnterInfo: HashOperations<String, String, String>? = null
//
//    @Resource(name = "redisTemplate")
//    private val valueOps: ValueOperations<String, String>? = null
//    fun createChatRoom(name: String?): ChatRoom {
//        val chatRoom: ChatRoom = ChatRoom.create(name)
//        chatRoom.roomId?.let { hashOpsChatRoom!!.put(CHAT_ROOMS, it, chatRoom) }
//        return chatRoom
//    }
//
//    fun findAllRoom(): List<ChatRoom> {
//        return hashOpsChatRoom!!.values(CHAT_ROOMS)
//    }
//
//    fun findRoomById(id: String?): ChatRoom? {
//        return hashOpsChatRoom!![CHAT_ROOMS, id!!]
//    }
//
//    fun setUserEnterInfo(sessionId: String, roomId: String) {
//        hashOpsEnterInfo!!.put(ENTER_INFO, sessionId, roomId)
//    }
//
//    fun getUserEnterRoomId(sessionId: String?): String? {
//        return hashOpsEnterInfo!![ENTER_INFO, sessionId!!]
//    }
//
//    fun removeUserEnterInfo(sessionId: String?) {
//        hashOpsEnterInfo!!.delete(ENTER_INFO, sessionId)
//    }
//
//    fun getUserCount(roomId: String): Long {
//        return java.lang.Long.valueOf(Optional.ofNullable(valueOps!![USER_COUNT + "_" + roomId]).orElse("0"))
//    }
//
//    fun plusUserCount(roomId: String): Long {
//        return Optional.ofNullable(valueOps!!.increment(USER_COUNT + "_" + roomId)).orElse(0L)
//    }
//
//    fun minusUserCount(roomId: String): Long {
//        return Optional.ofNullable(valueOps!!.decrement(USER_COUNT + "_" + roomId)).filter { count: Long -> count > 0 }
//            .orElse(0L)
//    }
//
//    companion object {
//        const val USER_COUNT = "USER_COUNT"
//        const val ENTER_INFO = "ENTER_INFO"
//
//        // Redis CacheKeys
//        private const val CHAT_ROOMS = "CHAT_ROOM"
//    }
//}