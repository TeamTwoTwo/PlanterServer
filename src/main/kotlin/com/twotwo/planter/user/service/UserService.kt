package com.twotwo.planter.user.service

import com.twotwo.planter.security.JwtTokenProvider
import com.twotwo.planter.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val jwtTokenProvider: JwtTokenProvider) {

}
