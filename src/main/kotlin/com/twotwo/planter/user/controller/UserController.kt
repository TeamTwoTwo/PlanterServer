package com.twotwo.planter.user.controller

import com.twotwo.planter.user.domain.UserStatus
import com.twotwo.planter.user.dto.GetMyPageRes
import com.twotwo.planter.user.dto.WithdrawUserRes
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{userId}")
    fun getMyPage(authentication: Authentication, @PathVariable userId: Long): BaseResponse<GetMyPageRes> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(user.id != userId){
            throw BaseException(USER_ID_NOT_MATCH)
        }

        return BaseResponse(GetMyPageRes(user.id!!, user.name, user.profileImg, user.email, 0, user.address, user.detailAddress))
    }

    @PatchMapping("/{userId}/withdrawal")
    fun withdrawUser(authentication: Authentication, @PathVariable userId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(user.id != userId){
            throw BaseException(USER_ID_NOT_MATCH)
        }

        val updatedUser = userService.updateUserStatus(userId, UserStatus.ACTIVE)

        return BaseResponse(WithdrawUserRes(updatedUser.id!!, updatedUser.status.toString().lowercase()))
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: String): BaseResponse<Any> {
        userService.deleteUser(userId.toLong())

        return BaseResponse(SUCCESS)
    }
}