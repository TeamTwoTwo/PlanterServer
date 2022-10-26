package com.twotwo.planter.auth.controller

import com.twotwo.planter.auth.service.AuthService
import com.twotwo.planter.auth.dto.*
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService, private val passwordEncoder: PasswordEncoder) {

    @PostMapping("signup")
    fun register(@RequestBody @Valid userRegisterReq: UserRegisterReq): BaseResponse<UserRegisterRes> {

        if(authService.existsEmail(userRegisterReq.email)) {
            throw BaseException(DUPLICATE_EMAIL)
        }
        if(authService.existsPhone(userRegisterReq.phone)) {
            throw BaseException(DUPLICATE_PHONE)
        }
        userRegisterReq.password = passwordEncoder.encode(userRegisterReq.password)
        val userRegisterRes: UserRegisterRes = authService.createUser(userRegisterReq)

        return BaseResponse(userRegisterRes)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody userLoginReq: UserLoginReq): BaseResponse<UserLoginRes> {
        if(!authService.existsEmail(userLoginReq.email)) {
            throw BaseException(USER_NOT_FOUND)
        }

        val user: User = authService.findUser(userLoginReq.email)

        if(!passwordEncoder.matches(userLoginReq.password, user.password)) {
            throw BaseException(CREDENTIAL_INVALID)
        }

        val userLoginRes = UserLoginRes(authService.login(user.id!!), user.id!!, user.email, user.name, user.birth, user.phone, user.address, user.detailAddress, user.latitude, user.longitude)

        return BaseResponse(userLoginRes)
    }

    @PostMapping("/send-code")
    fun sendCertificateCode(@Valid @RequestBody sendCodeReq: SendCodeReq): BaseResponse<Any> {
        authService.sendCertificateCode(sendCodeReq)
        return BaseResponse(SUCCESS)
    }

    @PostMapping("/verify-code")
    fun verifyCertificateCode(@Valid @RequestBody verifyCodeReq: VerifyCodeReq): BaseResponse<Any> {

        val result = authService.verifyCertificateCode(verifyCodeReq)
        if(result == 0){
            throw BaseException(INVALID_CODE)
        }
        return BaseResponse(SUCCESS)
    }

    @GetMapping("/check-duplication")
    fun checkDuplicate(@Valid @RequestParam checkDuplicateReq: CheckDuplicateReq): BaseResponse<Any> {
        if(!authService.existsEmail(checkDuplicateReq.email)) {
            throw BaseException(DUPLICATE_EMAIL)
        }

        if(!authService.existsPhone(checkDuplicateReq.phone)) {
            throw BaseException(DUPLICATE_PHONE)
        }

        return BaseResponse(SUCCESS)
    }
}