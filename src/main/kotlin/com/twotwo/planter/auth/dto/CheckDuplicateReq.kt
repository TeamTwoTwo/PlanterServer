package com.twotwo.planter.auth.dto

data class CheckDuplicateReq (
    val email: String?,
    val phone: String?,
    val nickname: String?
    )