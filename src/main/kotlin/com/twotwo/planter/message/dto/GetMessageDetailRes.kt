package com.twotwo.planter.message.dto


data class GetMessageDetailRes (
    val messageId: Long,
    val isSend: Boolean,
    val contents: String,
    val images: List<String>,
    val sentAt: String,
        )