package com.twotwo.planter.auth.dto

import org.hibernate.validator.constraints.Length

data class SendCodeReq (
    @field:Length(min=10, max=11,message="2005:휴대폰번호 형식이 잘못되었습니다.")
    val phone: String)