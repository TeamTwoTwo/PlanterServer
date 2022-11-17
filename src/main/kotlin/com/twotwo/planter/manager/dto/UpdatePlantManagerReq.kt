package com.twotwo.planter.manager.dto

import org.springframework.web.multipart.MultipartFile

data class UpdatePlantManagerReq (
    val isActive: Boolean,
    val images: List<MultipartFile>?,
    val imageUrl: List<String>?,
    val description: String?,
    val introduction: String?,
    val price: Int,
    val isPhoto: Boolean
        )