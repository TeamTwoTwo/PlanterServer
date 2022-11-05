package com.twotwo.planter.review.dto

data class GetReviewListRes (
    val id: Long,
    val profileImg: String?,
    val name: String,
    val date: String,
    val rate: Double,
    val contents: String,
    val images: List<String>
        )