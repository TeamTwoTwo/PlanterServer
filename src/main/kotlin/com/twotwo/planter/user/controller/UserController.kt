package com.twotwo.planter.user.controller

import com.twotwo.planter.user.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService, private val passwordEncoder: PasswordEncoder) {

}