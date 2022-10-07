package com.itime.sernachat.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*


open class Chat(
    @Id
    var id: String? = UUID.randomUUID().toString(),
    var name: String? = null,
    var active: Boolean? = false,
    var online: Boolean? = false,
    var lastMessage: CommonMessage? = null,
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    var created: LocalDateTime = LocalDateTime.now()
) : Serializable

