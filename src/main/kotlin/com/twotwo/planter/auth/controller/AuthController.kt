package com.twotwo.planter.auth

import com.twotwo.planter.user.domain.User
import com.twotwo.planter.user.dto.UserLoginReq
import com.twotwo.planter.user.dto.UserLoginRes
import com.twotwo.planter.user.dto.UserRegisterReq
import com.twotwo.planter.user.dto.UserRegisterRes
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService, private val passwordEncoder: PasswordEncoder) {

    @PostMapping("signup")
    fun register(@RequestBody userRegisterReq: UserRegisterReq): ResponseEntity<UserRegisterRes> {

        // 이메일 형식 체크
        // 비밀번호 형식 체크
        // 생년월일 형식체크

        if(authService.existsUser(userRegisterReq.email)) {
            //throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }
        userRegisterReq.password = passwordEncoder.encode(userRegisterReq.password)

        return ResponseEntity.ok(authService.createUser(userRegisterReq))
    }

    @PostMapping("/login")
    fun login(@RequestBody userLoginReq: UserLoginReq): ResponseEntity<UserLoginRes> {
        if(!authService.existsUser(userLoginReq.email)) {
            //throw BaseException(BaseResponseCode.USER_NOT_FOUND)
        }

        val user: User = authService.findUser(userLoginReq.email)

        if(!passwordEncoder.matches(userLoginReq.password, user.password)) {
            //throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }

        return ResponseEntity.ok(authService.login(userLoginReq))
    }

}