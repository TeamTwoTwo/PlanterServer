package com.twotwo.planter.message.dto

data class SendMessageReq (
    val plantManagerId: Long,
    val contents: String,
    val images: List<String>?
        )