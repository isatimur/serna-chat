package com.itime.sernachat.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime
import java.util.*

data class User(
    val id: UUID? = UUID.randomUUID(),
    val username: String,
    val email: String,
    var secret: String? = null,
    var role: Role? = Role.ROLE_USER,
    var isAuthenticated: Boolean? = false,
    var firstName: String? = null,
    var lastName: String? = null,
    var avatar: String? = null,
    var customJson: String? = null,
    var online: Boolean? = false,
    @field:JsonSerialize(using = LocalDateTimeSerializer::class)
    @field:JsonDeserialize(using = LocalDateTimeDeserializer::class)
    var created: LocalDateTime? = LocalDateTime.now()
)