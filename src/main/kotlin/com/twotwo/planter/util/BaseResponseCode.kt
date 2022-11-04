package com.twotwo.planter.util

import org.springframework.http.HttpStatus

enum class BaseResponseCode(isSuccess: Boolean, code: Int, status: HttpStatus, message: String) {
    SUCCESS(true, 1000, HttpStatus.OK,"성공"),

    EMAIL_OR_PHONE_REQUIRED(false, 2006, HttpStatus.OK, "이메일 또는 휴대폰번호를 입력해주세요"),
    CATEGORY_VALUE_INVAID(false, 2007, HttpStatus.OK, "식물 관리자 카테고리 값이 잘못되었습니다"),
    SORT_VALUE_INVALID(false, 2008, HttpStatus.OK, "식물 관리자 정렬 값이 잘못되었습니다"),

    DUPLICATE_EMAIL(false, 3001, HttpStatus.OK, "중복된 이메일입니다"),
    INVALID_CODE(false, 3002, HttpStatus.OK, "인증번호가 일치하지 않습니다"),
    DUPLICATE_PHONE(false, 3003, HttpStatus.OK, "중복된 휴대폰번호입니다"),
    USER_NOT_FOUND(false, 3004, HttpStatus.OK, "이메일, 비밀번호가 일치하지 않습니다"),
    CREDENTIAL_INVALID(false, 3005, HttpStatus.OK, "이메일, 비밀번호가 일치하지 않습니다"),
    PLANTER_MANAGER_NOT_FOUND(false, 3006, HttpStatus.OK, "존재하지 않는 식물관리자입니다"),

    AUTHENTICATE_FAILED(false, 4000, HttpStatus.OK, "JWT 토큰을 확인해주세요");

    val isSuccess: Boolean = isSuccess
    val code: Int = code
    val status: HttpStatus = status
    val message: String = message
}