package com.twotwo.planter.user.service

import com.twotwo.planter.security.JwtTokenProvider
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.user.domain.UserStatus
import com.twotwo.planter.user.repository.UserRepository
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository, private val jwtTokenProvider: JwtTokenProvider) {
    fun deleteUser(userId: Long) {
        userRepository.deleteById(userId)
    }
    fun findUser(email: String): User {
        return userRepository.findByEmail(email)
    }

    fun updateUserStatus(userId: Long, status: UserStatus): User {
        val user = userRepository.findUserById(userId)

        if(user.status === UserStatus.DELETED){
            throw BaseException(BaseResponseCode.USER_ALREADY_DELETED)
        }

        user.status = UserStatus.DELETED
        userRepository.save(user)

        return user
    }
}
