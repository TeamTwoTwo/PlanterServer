package com.twotwo.planter.manager.dto

data class UpdatePlantManagerRes (
    val plantManagerId: Long,
    val isActive: Boolean,
    val images: List<String>?,
    val description: String?,
    val introduction: String?,
    val careOptions: List<CareOption>,
    val isPhoto: Boolean
        )