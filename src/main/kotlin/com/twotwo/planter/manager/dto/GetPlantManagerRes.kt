package com.twotwo.planter.manager.dto

import com.twotwo.planter.review.domain.Review

data class GetPlantManagerRes (
    val id: Long,
    val name: String,
    val category: Int,
    val profileImg: String,
    val distance: Double,
    val isPhoto: Boolean,
    val rate: Double,
    val description: String,
    val caringPrice: Int,
    val pruningPrice: Int,
    val images: List<String>?,
    val introduction: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val reviews: List<Review>?,
    val numOfReview: Int,
)