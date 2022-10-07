package com.itime.sernachat.domain

import java.time.LocalDateTime

class TextMessage(
    messageId: String,
    chatId: String,
    sender: ChatMember,
    type: String,
    var content: String,
    messageDate: LocalDateTime,
) : CommonMessage(messageId, chatId, sender, type, messageDate)