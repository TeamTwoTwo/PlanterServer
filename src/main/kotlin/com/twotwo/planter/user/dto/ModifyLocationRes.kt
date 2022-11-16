package com.twotwo.planter.user.dto

data class ModifyLocationRes (
    val userId: Long,
    val address: String,
    val detailAddress: String?,
    val simpleAddress: String?
        )