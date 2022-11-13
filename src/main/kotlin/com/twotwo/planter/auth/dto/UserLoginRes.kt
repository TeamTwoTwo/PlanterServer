package com.twotwo.planter.auth.dto

data class UserLoginRes (
    val token: String,
    val userId: Long,
    val email: String,
    val name: String,
    val birth: String,
    val phone: String,
    val address: String,
    val detailAddress: String?,
    val latitude: Double?,
    val longitude: Double?
    )