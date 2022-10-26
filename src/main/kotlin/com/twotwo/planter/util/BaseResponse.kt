package com.twotwo.planter.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("isSuccess", "code", "message", "result")
class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private val isSuccess: Boolean
    @JsonProperty("message")
    private val message: String
    @JsonProperty("code")
    private val code: Int

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("result")
    private var result: T? = null

    // success
    constructor(result: T) {
        this.isSuccess = BaseResponseCode.SUCCESS.isSuccess
        this.message = BaseResponseCode.SUCCESS.message
        this.code = BaseResponseCode.SUCCESS.code
        this.result = result
    }

    // fail
    constructor(status: BaseResponseCode) {
        this.isSuccess = status.isSuccess
        this.message = status.message
        this.code = status.code
    }
}
