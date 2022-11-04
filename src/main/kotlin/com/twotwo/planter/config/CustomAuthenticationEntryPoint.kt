package com.twotwo.planter.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode
import com.twotwo.planter.util.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        request!!
        val errorResponse: BaseResponse<Any> = BaseResponse(BaseResponseCode.AUTHENTICATE_FAILED)
        response?.status = HttpStatus.UNAUTHORIZED.value()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.characterEncoding = "UTF-8"
        response?.writer?.println(convertObjectToJson(errorResponse))
    }

    private fun convertObjectToJson(obj: Any): String? {
        val mapper = ObjectMapper().registerModule(KotlinModule()).registerModule(JavaTimeModule())
        return mapper.writeValueAsString(obj)
    }
}