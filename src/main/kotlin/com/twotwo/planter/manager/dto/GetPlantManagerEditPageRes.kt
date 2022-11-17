package com.twotwo.planter.manager.dto

data class GetPlantManagerEditPageRes (
    val plantManagerId: Long,
    val isActive: Boolean,
    val imageUrl: List<String>,
    val introduction: String?,
    val description: String?,
    val careOption: List<CareOption>?,
    val isPhoto: Boolean
        )