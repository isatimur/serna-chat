package com.itime.sernachat.domain

import java.util.*

class GroupChat(
    id: String? =UUID.randomUUID().toString(),
    members: MutableList<ChatMember>,
    owner: ChatMember,
    image: String,
    name: String,
    active: Boolean,
    online: Boolean,
    lastMessage: CommonMessage
) : Chat(id, name, active, online, lastMessage)