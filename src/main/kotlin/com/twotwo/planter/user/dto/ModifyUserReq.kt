package com.twotwo.planter.user.dto

import org.springframework.web.multipart.MultipartFile

data class ModifyUserReq (
    val profileImg: MultipartFile?,
    val profileImgUrl: String?,
    val nickname: String?,
)