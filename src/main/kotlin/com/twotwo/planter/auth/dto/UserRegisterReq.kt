package com.twotwo.planter.auth.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class UserRegisterReq(
    @field:NotNull
    @field:Email(message="2001:이메일 형식이 잘못되었습니다")
    val email: String,

    @field:NotNull
    val name: String,

    @field:NotNull
    val nickname: String,

    @field:NotNull
    val simpleAddress: String,

    @field:Length(min=8, max=20, message="2002:비밀번호 길이는 8-20이어야 합니다")
    @field:Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]*$", message="2003:비밀번호는 문자,숫자,특수문자를 모두 포함해야 합니다")
    var password: String,

    @field:Pattern(regexp="^([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))$", message="2004:생년월일은 YYMMDD형식으로 입력해주세요")
    val birth: String,

    @field:Length (min=10, max=11,message="2005:휴대폰번호 형식이 잘못되었습니다.")
    val phone: String,

    val address: String,
    val detailAddress: String?,
    )