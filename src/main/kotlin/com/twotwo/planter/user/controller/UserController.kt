package com.twotwo.planter.user.controller

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.user.domain.UserStatus
import com.twotwo.planter.user.dto.*
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService, private val plantManagerService: PlantManagerService) {

    @GetMapping("/{userId}")
    fun getMyPage(authentication: Authentication, @PathVariable userId: Long): BaseResponse<GetMyPageRes> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(user.id != userId){
            throw BaseException(USER_ID_NOT_MATCH)
        }
        var phone = user.phone.substring(0, 3) + "-"
        if(user.phone.length == 10){
            phone += user.phone.substring(3, 6)
            phone += "-"
            phone += user.phone.substring(6, user.phone.length)
        }
        else {
            phone += user.phone.substring(3, 7)
            phone += "-"
            phone += user.phone.substring(7, user.phone.length)
        }
        return BaseResponse(GetMyPageRes(user.id!!, user.name, user.profileImg, user.email, 0, user.address, user.detailAddress, phone, user.nickname))
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

    @PatchMapping("/{userId}/location")
    fun modifyLocation(authentication: Authentication, @PathVariable userId: Long, @RequestBody modifyLocationReq: ModifyLocationReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(user.id != userId){
            throw BaseException(USER_ID_NOT_MATCH)
        }

        val updatedUser = userService.updateUserLocation(userId, modifyLocationReq.address, modifyLocationReq.detailAddress, modifyLocationReq.simpleAddress)

        return BaseResponse(ModifyLocationRes(updatedUser.id!!, updatedUser.address, updatedUser.detailAddress, updatedUser.simpleAddress))
    }

    @PatchMapping("/{userId}")
    fun modifyLocation(authentication: Authentication, @PathVariable userId: Long, @ModelAttribute modifyUserReq: ModifyUserReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(user.id != userId){
            throw BaseException(USER_ID_NOT_MATCH)
        }

        val updatedUser = userService.updateUserInfo(userId, modifyUserReq.profileImg, modifyUserReq.profileImgUrl, modifyUserReq.nickname)

        return BaseResponse(ModifyUserRes(updatedUser.id!!, updatedUser.profileImg, updatedUser.nickname))
    }

    @GetMapping("/{userId}/plant-manager")
    fun getPlantManagerEditPage(authentication: Authentication): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val response = plantManagerService.getPlantManagerByUser(user)
        return BaseResponse(response)
    }

}