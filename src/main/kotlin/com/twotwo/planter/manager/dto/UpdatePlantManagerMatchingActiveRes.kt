package com.twotwo.planter.manager.dto

data class UpdatePlantManagerMatchingActiveRes (
    val userId: Long,
    val plantManagerId: Long,
    val isActive: Boolean
        )