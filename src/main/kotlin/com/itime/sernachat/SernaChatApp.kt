package com.itime.sernachat

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.itime.sernachat.domain.Chat
import com.itime.sernachat.domain.ChatMember
import com.itime.sernachat.domain.TextMessage
import com.itime.sernachat.service.ChatService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.io.IOException
import java.time.LocalDateTime
import java.util.*


@SpringBootApplication
class SernaChatApp {

    @Bean
    fun runner(chatService: ChatService): CommandLineRunner? {
        return CommandLineRunner { args: Array<String> ->
            // read json and write to db
            try {
                val chats: MutableList<Chat> = ObjectMapper().readValue(
                    this::class.java.classLoader.getResource("allChats.json").readText(),
                    object : TypeReference<MutableList<Chat>>() {}) as MutableList<Chat>

                chatService.save(chats)
                println("Users Saved!")
            } catch (e: IOException) {
                println("Unable to save users: " + e.message)
            }

            val message: TextMessage = TextMessage(
                UUID.randomUUID().toString(), "356c3278-3eb3-11ed-b878-0242ac120002",
                ChatMember(
                    "356c3278-3eb3-11ed-b878-0242ac120002",
                    "Tim Hover",
                    "https://i.pravatar.cc/150?img=15",
                    false
                ),
                "", "Blabla!", LocalDateTime.now()
            )
                chatService.saveMessages(mutableListOf(message))
                println("Messages Saved!")
        }
    }


}

fun main(args: Array<String>) {
    runApplication<SernaChatApp>(*args)
}