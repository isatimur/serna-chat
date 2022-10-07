package com.itime.sernachat.repository

import com.itime.sernachat.domain.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository(private val store: ConcurrentHashMap<UUID, User> = ConcurrentHashMap<UUID, User>()) {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)
    fun getOrCreate(user: User): User {
        val foundUser: User = store.entries.stream()
            .filter { it ->
                it.value.email == user.email
            }.map { it -> it.value }
            .findFirst().orElse(
                User(UUID.randomUUID(), user.username, user.email, user.secret, user.role)
            )
        store[foundUser.id!!] = foundUser
        logger.info("USER STORE: {}", store.entries.toString())
        return foundUser

    }
}

