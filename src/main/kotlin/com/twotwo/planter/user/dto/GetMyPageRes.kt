package com.twotwo.planter.user.dto

data class GetMyPageRes (
    val userId: Long,
    val name: String,
    val profileImg: String?,
    val email: String,
        )