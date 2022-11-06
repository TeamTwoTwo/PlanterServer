package com.twotwo.planter.matching.dto

data class GetMatchingDetailRes (
    val matchingId: Long,
    val plantManagerId: Long,
    val profileImg: String,
    val name: String,
    val category: Int,
    val requestAt: String,
    val status: String,
    val service: List<PlantServiceRes>,
    val totalPrice: Int,
    val startDate: String,
    val endDate: String,
    val totalDate: Long,
    val pickupType: Int
        )