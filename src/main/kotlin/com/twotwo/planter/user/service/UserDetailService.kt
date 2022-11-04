package com.twotwo.planter.user.service

import com.twotwo.planter.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val userRepository: UserRepository): UserDetailsService{
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findUserById(username.toLong())
            //.orElseThrow{ BaseException(BaseResponseCode.USER_NOT_FOUND) }
    }
}