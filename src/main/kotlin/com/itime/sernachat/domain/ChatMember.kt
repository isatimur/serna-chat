package com.itime.sernachat.domain

import java.util.*

data class ChatMember(
    val id: String? = UUID.randomUUID().toString(),
    var fullName: String? = null,
    var avatar: String? = null,
    var deletedChat: Boolean? = false
)
