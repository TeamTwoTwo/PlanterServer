package com.twotwo.planter.auth.dto

data class UserLoginRes (
    val token: String,
    val userId: Long,
    )