package com.twotwo.planter.user.service

import com.twotwo.planter.security.JwtTokenProvider
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.user.repository.UserRepository
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
}
