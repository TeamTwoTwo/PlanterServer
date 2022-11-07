package com.twotwo.planter.message.dto

import org.springframework.web.multipart.MultipartFile

data class SendMessageReq (
    val plantManagerId: Long,
    val contents: String,
    val images: List<MultipartFile>?
        )