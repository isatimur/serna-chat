package com.itime.sernachat.domain

import java.time.LocalDateTime

class ImageMessage(
    messageId: String,
    chatId: String,
    sender: ChatMember,
    type: String,
    var imageLink: String,
    messageDate: LocalDateTime
) : CommonMessage(messageId, chatId, sender, type, messageDate)