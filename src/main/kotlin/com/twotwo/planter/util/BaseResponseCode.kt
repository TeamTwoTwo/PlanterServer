package com.twotwo.planter.util

import org.springframework.http.HttpStatus

enum class BaseResponseCode(isSuccess: Boolean, code: Int, status: HttpStatus, message: String) {
    SUCCESS(true, 1000, HttpStatus.OK,"성공"),

    EMAIL_OR_PHONE_REQUIRED(false, 2006, HttpStatus.OK, "이메일 또는 휴대폰번호를 입력해주세요"),
    CATEGORY_VALUE_INVALID(false, 2007, HttpStatus.OK, "식물 관리자 카테고리 값이 잘못되었습니다"),
    SORT_VALUE_INVALID(false, 2008, HttpStatus.OK, "식물 관리자 정렬 값이 잘못되었습니다"),
    MATCHING_STATUS_INVALID(false, 2009, HttpStatus.OK, "매칭 상태 입력값은 cancel 또는 complete 여야합니다"),

    DUPLICATE_EMAIL(false, 3001, HttpStatus.OK, "중복된 이메일입니다"),
    INVALID_CODE(false, 3002, HttpStatus.OK, "인증번호가 일치하지 않습니다"),
    DUPLICATE_PHONE(false, 3003, HttpStatus.OK, "중복된 휴대폰번호입니다"),
    USER_NOT_FOUND(false, 3004, HttpStatus.OK, "이메일, 비밀번호가 일치하지 않습니다"),
    CREDENTIAL_INVALID(false, 3005, HttpStatus.OK, "이메일, 비밀번호가 일치하지 않습니다"),
    PLANTER_MANAGER_NOT_FOUND(false, 3006, HttpStatus.OK, "존재하지 않는 식물관리자입니다"),
    MATCHING_NOT_FOUND(false, 3007, HttpStatus.OK, "존재하지 않는 매칭입니다"),
    MATCHING_STATUS_NOT_REQUEST(false, 3008, HttpStatus.OK, "매칭이 요청중 상태가 아닙니다"),
    MATCHING_STATUS_NOT_CARE(false, 3009, HttpStatus.OK, "매칭이 케어 진행중 상태가 아닙니다"),
    MATCHING_USER_NOT_MATCH(false, 3010, HttpStatus.OK, "매칭 요청한 사용자와 일치하지 않는 사용자입니다"),
    REVIEW_ALREADY_EXIST(false, 3011, HttpStatus.OK, "이미 리뷰를 작성한 매칭입니다"),
    PLANT_CARE_OPTION_NOT_FOUND(false, 3012, HttpStatus.OK, "존재하지 않는 케어 옵션입니다"),

    AUTHENTICATE_FAILED(false, 4000, HttpStatus.OK, "JWT 토큰을 확인해주세요"),
    FILE_UPLOAD_FAIL(false, 4002, HttpStatus.OK, "파일업로드에 실패했습니다");

    val isSuccess: Boolean = isSuccess
    val code: Int = code
    val status: HttpStatus = status
    val message: String = message
}