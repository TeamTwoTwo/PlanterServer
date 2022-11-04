package com.twotwo.planter.review.dto

data class WriteReviewReq (
    val plantManagerId: Long,
    val rate: Double,
    val contents: String,
    val images: List<String>?
        )