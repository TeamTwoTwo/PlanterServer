package com.twotwo.planter.matching.controller

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.matching.domain.MatchingStatus
import com.twotwo.planter.matching.dto.GetMatchingListRes
import com.twotwo.planter.matching.service.MatchingService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("")
class MatchingController(private val matchingService: MatchingService, private val userService: UserService, private val plantManagerService: PlantManagerService, private val plantManagerUtil:  PlantManagerUtil) {
    val matchingEnumList = arrayListOf(MatchingStatus.REQUEST, MatchingStatus.CARE, MatchingStatus.COMPLETE, MatchingStatus.CANCLE)

    @GetMapping("/matchings")
    fun getMatchingList(authentication: Authentication): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val matchings = matchingService.getMatchingList(user.id!!)
        val response = arrayListOf<GetMatchingListRes>()

        for (matching in matchings) {
            response.add(
                GetMatchingListRes(
                    matching.id!!,
                    matching.plantManager.id!!,
                    matching.plantManager.profileImg,
                    matching.plantManager.name,
                    plantManagerUtil.getCategoryInt(matching.plantManager.category),
                    matching.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    matchingEnumList.indexOf(matching.status)
                )
            )
        }

        return BaseResponse(response)
    }

    /*@GetMapping("/plant-managers/{plantManagerId}/matchings")
    fun getMatching(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val matchings = matchingService.getMatchingDetail(user.id!!, plantManagerId)
        val response = arrayListOf<GetMatchingDetailRes>()

        for (matching in matchings) {
            val images = arrayListOf<String>()
            if (matching.images !== null) {
                for (image in matching.images!!) {
                    images.add(image!!.imageUrl)
                }
            }
            response.add(
                GetMatchingDetailRes(
                    matching.id!!, matching.sender === SenderType.USER,
                    matching.contents, images, matching.createdAt!!.format(DateTimeFormatter.ofPattern("a hh:mm"))
                )
            )
        }

        return BaseResponse(response)
    }

    @PostMapping("/matchings")
    fun sendMatching(authentication: Authentication, @RequestBody sendMatchingReq: SendMatchingReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)
        val plantManager = plantManagerService.getPlantManager(sendMatchingReq.plantManagerId)

        val matching = matchingService.createMatching(
            Matching(
                sendMatchingReq.contents,
                MatchingStatus.ACTIVE,
                false,
                SenderType.USER,
                user,
                plantManager
            )
        )
        if (sendMatchingReq.images !== null) {
            for (image in sendMatchingReq.images) {
                matchingImgService.createMatchingImg(MatchingImg(image, matching))
            }
        }
        val response = SendMatchingRes(matching.id!!)

        return BaseResponse(response)
    }

    @PatchMapping("/plant-managers/{plantManagerId}/matchings/status")
    fun deleteMatching(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        matchingService.updateMatchingStatusToDelete(user.id!!, plantManagerId)

        return BaseResponse(BaseResponseCode.SUCCESS)
    }*/
}