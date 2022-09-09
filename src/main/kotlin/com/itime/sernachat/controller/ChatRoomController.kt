package com.itime.sernachat.controller

import com.itime.sernachat.domain.ChatRoom
import com.itime.sernachat.domain.LoginInfo
import com.itime.sernachat.repository.ChatRoomRepository
import com.itime.sernachat.service.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer

@Controller
@RequestMapping("/chat")
class ChatRoomController(
    val chatRoomRepository: ChatRoomRepository,
    val jwtTokenProvider: JwtTokenProvider
) {
    @get:ResponseBody
    @get:GetMapping("/user")
    val userInfo: LoginInfo
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            val name = authentication.name
            return LoginInfo(name, jwtTokenProvider.generateToken(name))
        }

    @GetMapping("/room")
    fun rooms(model: Model?): String {
        return "/chat/room"
    }

    @GetMapping("/rooms")
    @ResponseBody
    fun room(): List<ChatRoom> {
        val chatRooms: List<ChatRoom> = chatRoomRepository.findAllRoom()
        chatRooms.stream()
            .forEach { room: ChatRoom ->
                room.userCount = room.roomId?.let { chatRoomRepository.getUserCount(it) }!!
            }
        return chatRooms
    }

    @PostMapping("/room")
    @ResponseBody
    fun createRoom(@RequestParam name: String?): ChatRoom {
        return chatRoomRepository.createChatRoom(name)
    }

    @GetMapping("/room/enter/{roomId}")
    fun roomDetail(model: Model, @PathVariable roomId: String?): String {
        model.addAttribute("roomId", roomId)
        return "/chat/roomdetail"
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    fun roomInfo(@PathVariable roomId: String?): ChatRoom {
        return chatRoomRepository.findRoomById(roomId)!!
    }
}