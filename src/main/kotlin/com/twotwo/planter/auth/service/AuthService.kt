package com.twotwo.planter.auth.service

import com.twotwo.planter.security.JwtTokenProvider
import com.twotwo.planter.auth.dto.SendCodeReq
import com.twotwo.planter.auth.dto.UserRegisterReq
import com.twotwo.planter.auth.dto.UserRegisterRes
import com.twotwo.planter.auth.dto.VerifyCodeReq
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.user.domain.UserStatus
import com.twotwo.planter.user.repository.UserRepository
import com.twotwo.planter.user.service.UserService
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

@Service
class AuthService(private val userRepository: UserRepository, private val userService: UserService, private val jwtTokenProvider: JwtTokenProvider, private val certificateCodeService: CertificateCodeService, private val smsService: SmsService) {

    fun findUser(email: String): User {
        return userRepository.findByEmail(email)
        //.orElseThrow{BaseException(BaseResponseCode.USER_NOT_FOUND)}
    }

    fun existsEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
        //.orElseThrow{BaseException(BaseResponseCode.DUPLICATE_EMAIL)}
    }

    fun existsPhone(phone: String): Boolean {
        return userRepository.existsByPhone(phone)
        //.orElseThrow{BaseException(BaseResponseCode.DUPLICATE_EMAIL)}
    }

    fun createUser(userRegisterReq: UserRegisterReq): UserRegisterRes {
        val user = User(userRegisterReq.name, userRegisterReq.email, userRegisterReq.password, userRegisterReq.birth, userRegisterReq.phone,
            userRegisterReq.address, userRegisterReq.detailAddress, 	37.59350051061, 127.00188398407,
            null, UserStatus.ACTIVE, userRegisterReq.nickname, userRegisterReq.simpleAddress)
        val createdUser = userRepository.save(user)
        val token = jwtTokenProvider.createToken(createdUser.id!!)

        return UserRegisterRes(token, createdUser.id!!)
    }

    fun login(userId: Long): String {
        val token: String = jwtTokenProvider.createToken(userId)

        return token
    }

    fun sendCertificateCode(sendCodeReq: SendCodeReq): Int {
        val certificateCode = (SecureRandom().nextInt(100000) % 100000).toString()

        certificateCodeService.createSmsCertification(sendCodeReq.phone, certificateCode)
        smsService.sendSms(sendCodeReq.phone, "[플랜터] 인증번호 [" + certificateCode + "]를 입력해 주세요.")

        return 1
    }

    fun verifyCertificateCode(verifyCodeReq: VerifyCodeReq): Int {

        val code = certificateCodeService.getSmsCertification(verifyCodeReq.phone)

        if(code == verifyCodeReq.code) {
            return 1
        }
        return 0
    }
}
