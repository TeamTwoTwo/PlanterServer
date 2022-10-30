package com.twotwo.planter.auth.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class CheckDuplicateReq (
    @field:Email(message="2001:이메일 형식이 잘못되었습니다")
    val email: String?,
    @field:Length(min=10, max=11,message="2005:휴대폰번호 형식이 잘못되었습니다.")
    val phone: String?
    )