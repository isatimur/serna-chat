package com.itime.sernachat.domain

import java.util.*

open class DirectChat(
    id: String? = UUID.randomUUID().toString(),
    senderId: String,
    recepientId: String,
    recepientName: String,
    active: Boolean,
    online: Boolean,
    lastMessage: CommonMessage
) : Chat(id, recepientName, active, online, lastMessage)