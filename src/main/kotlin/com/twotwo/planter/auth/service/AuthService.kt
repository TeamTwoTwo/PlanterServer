package com.twotwo.planter.auth

import com.twotwo.planter.user.domain.User
import com.twotwo.planter.user.dto.UserLoginReq
import com.twotwo.planter.user.dto.UserLoginRes
import com.twotwo.planter.user.dto.UserRegisterReq
import com.twotwo.planter.user.dto.UserRegisterRes
import com.twotwo.planter.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(private val userRepository: UserRepository, private val jwtTokenProvider: JwtTokenProvider) {

    fun findUser(email: String): User {
        return userRepository.findByEmail(email)
        //.orElseThrow{BaseException(BaseResponseCode.USER_NOT_FOUND)}
    }

    fun existsUser(email: String): Boolean {
        return userRepository.existsByEmail(email)
        //.orElseThrow{BaseException(BaseResponseCode.DUPLICATE_EMAIL)}
    }

    fun createUser(userRegisterReq: UserRegisterReq): UserRegisterRes {
        val user = User(userRegisterReq.name, userRegisterReq.email, userRegisterReq.password, userRegisterReq.birth, userRegisterReq.phone)
        userRepository.save(user)

        return UserRegisterRes(user.id!!, user.email)
    }

    fun login(userLoginReq: UserLoginReq): UserLoginRes {
        val token: String = jwtTokenProvider.createToken(userLoginReq.email)

        return UserLoginRes(token)
    }
}
