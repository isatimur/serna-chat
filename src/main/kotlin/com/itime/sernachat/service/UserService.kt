package com.itime.sernachat.service

import com.itime.sernachat.domain.User
import com.itime.sernachat.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserService(val userRepository: UserRepository) {
    fun getOrCreate(user: User): User{
        return userRepository.getOrCreate(user)
    }
}