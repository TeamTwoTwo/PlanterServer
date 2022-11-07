package com.twotwo.planter.review.dto

import org.springframework.web.multipart.MultipartFile

data class WriteReviewReq (
    val plantManagerId: Long,
    val rate: Double,
    val contents: String,
    val images: List<MultipartFile>?
        )