package com.twotwo.planter.manager.dto

data class GetPlantManagerListRes (
    val id: Long,
    val name: String,
    val category: Int,
    val profileImg: String?,
    val distance: Double,
    val isPhoto: Boolean,
    val rate: Double,
    val description: String,
    val minPrice: Int,
        )