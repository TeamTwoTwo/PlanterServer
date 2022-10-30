package com.twotwo.planter.util

import org.springframework.http.HttpStatus

enum class BaseResponseCode(isSuccess: Boolean, code: Int, status: HttpStatus, message: String) {
    SUCCESS(true, 1000, HttpStatus.OK,"성공"),

    EMAIL_OR_PHONE_REQUIRED(false, 2006, HttpStatus.BAD_REQUEST, "이메일 또는 휴대폰번호를 입력해주세요"),

    DUPLICATE_EMAIL(false, 3001, HttpStatus.CONFLICT, "중복된 이메일입니다"),
    INVALID_CODE(false, 3002, HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다"),
    DUPLICATE_PHONE(false, 3003, HttpStatus.CONFLICT, "중복된 휴대폰번호입니다"),
    USER_NOT_FOUND(false, 3004, HttpStatus.NOT_FOUND, "이메일, 비밀번호가 일치하지 않습니다"),
    CREDENTIAL_INVALID(false, 3005, HttpStatus.BAD_REQUEST, "이메일, 비밀번호가 일치하지 않습니다")
;

    val isSuccess: Boolean = isSuccess
    val code: Int = code
    val status: HttpStatus = status
    val message: String = message
}