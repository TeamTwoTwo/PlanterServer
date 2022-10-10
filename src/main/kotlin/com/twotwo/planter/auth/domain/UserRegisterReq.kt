package com.twotwo.planter.user.dto

data class UserRegisterReq(
    val email: String,
    val name: String,
    var password: String,
    val birth: String,
    val phone: String)