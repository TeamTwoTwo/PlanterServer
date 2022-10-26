package com.twotwo.planter.user.controller

import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: String): BaseResponse<Any> {
        userService.deleteUser(userId.toLong())

        return BaseResponse(BaseResponseCode.SUCCESS)
    }
}