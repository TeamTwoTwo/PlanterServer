package com.twotwo.planter.config

import com.twotwo.planter.common.dto.ErrorResponse
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.lang.model.type.NullType


@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<Any> {
        return ResponseEntity.status(e.baseResponseCode.status)
            .body(BaseResponse<NullType>(e.baseResponseCode))
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val error = ex.bindingResult.fieldErrors[0]
        if(error.defaultMessage!!.split(':').size > 1){
            val code = error.defaultMessage!!.split(':')[0].toInt()
            val message = error.defaultMessage!!.split(':')[1]
            val errorResponse = ErrorResponse(false, code, message)
            return ResponseEntity(errorResponse, headers, HttpStatus.OK)
        }
        return ResponseEntity(ErrorResponse(false, 4000, error.defaultMessage!!), headers, HttpStatus.OK)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        var errorResponseDto: ErrorResponse
        errorResponseDto = if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            ErrorResponse(false, 5001, "Internal Server Error")
        } else if(status == HttpStatus.BAD_REQUEST) {
            ErrorResponse(false, 4000, "Bad Request")
        } else {
            ErrorResponse(false, status.value(), ex.message!!)
        }
        if(errorResponseDto.code == 5000){
            return ResponseEntity(errorResponseDto, headers, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(errorResponseDto, headers, HttpStatus.OK)
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    protected fun handleMaxUploadSizeExceededException(
        e: MaxUploadSizeExceededException?
    ): ResponseEntity<ErrorResponse?>? {
        val response = ErrorResponse(false, 4001, "파일 용량 초과")
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }
}