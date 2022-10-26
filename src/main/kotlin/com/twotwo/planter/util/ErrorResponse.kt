package com.twotwo.planter.util

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    @field:JsonProperty("isSuccess")
    var isSuccess: Boolean? = false,

    @field:JsonProperty("errorCode")
    var errorCode: String? = null,

    @field:JsonProperty("errorMessage")
    var errorMessage: String? = null,
)

data class Error(
    var field: String? = null,
    var message: String? = null,
    var value: Any? = null
)