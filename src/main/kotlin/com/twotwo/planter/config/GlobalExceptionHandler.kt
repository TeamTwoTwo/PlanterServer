package com.twotwo.planter.config

import com.twotwo.planter.common.dto.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val error = ex.bindingResult.fieldErrors[0]
        if(error.defaultMessage!!.split(':').size > 1){
            val errorCode = error.defaultMessage!!.split(':')[0].toInt()
            val errorMessage = error.defaultMessage!!.split(':')[1]
            val errorResponse = ErrorResponse(false, errorCode, errorMessage)
            return ResponseEntity(errorResponse, headers, status)
        }
        return ResponseEntity(error, headers, status)
    }
}