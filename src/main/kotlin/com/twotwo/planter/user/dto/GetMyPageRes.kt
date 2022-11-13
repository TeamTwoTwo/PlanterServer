package com.twotwo.planter.user.dto

data class GetMyPageRes (
    val userId: Long,
    val name: String,
    val profileImg: String?,
    val email: String,
    val category: Int,
    val address: String,
    val detailAddress: String?,
    val phone: String?,
        )