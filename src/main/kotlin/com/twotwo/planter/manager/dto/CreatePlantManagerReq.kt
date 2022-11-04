package com.twotwo.planter.manager.dto

import com.twotwo.planter.manager.domain.PlantManagerCategory

data class CreatePlantManagerReq (
    val address: String,
    val caringPrice: Int,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val profileImg: String,
    val pruningPrice: Int,
    val category: PlantManagerCategory,
    val is_photo: Boolean,
    val introduction: String
    )