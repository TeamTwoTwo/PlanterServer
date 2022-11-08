package com.twotwo.planter.matching.controller

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.matching.domain.MatchingStatus
import com.twotwo.planter.matching.dto.*
import com.twotwo.planter.matching.service.MatchingService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.validation.Valid

@RestController
@RequestMapping("")
class MatchingController(private val matchingService: MatchingService, private val userService: UserService, private val plantManagerService: PlantManagerService, private val plantManagerUtil:  PlantManagerUtil) {
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
                    matching.status.toString().lowercase()
                )
            )
        }

        return BaseResponse(response)
    }

    @GetMapping("/matchings/{matchingId}")
    fun getMatchingDetail(authentication: Authentication, @PathVariable matchingId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val matching = matchingService.getMatchingDetail(matchingId)
        val service = arrayListOf<PlantServiceRes>()
        var totalPrice = 0
        for(plant in matching.plants){
            service.add(PlantServiceRes(plant.name, plant.count, plant.plantCareOption.price, plant.plantCareOption.name))
            totalPrice += plant.plantCareOption.price * plant.count
        }
        val response = GetMatchingDetailRes(matching.id!!, matching.plantManager.id!!, matching.plantManager.profileImg, matching.plantManager.name,
            plantManagerUtil.getCategoryInt(matching.plantManager.category),
            matching.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), matching.status.toString().lowercase(),
            service, totalPrice, matching.startDate.format(DateTimeFormatter.ofPattern("MM.dd")), matching.endDate.format(DateTimeFormatter.ofPattern("MM.dd")),
            ChronoUnit.DAYS.between(matching.startDate, matching.endDate), matching.pickUpType.ordinal, 1
            )

        return BaseResponse(response)
    }

    /*@PostMapping("/matchings")
    fun createMatching(authentication: Authentication, @RequestBody createMatchingReq: CreateMatchingReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val plantManager = plantManagerService.createMatching(sendMatchingReq.plantManagerId)

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
    }*/

    @PatchMapping("/matchings/{matchingId}")
    fun modifyMatchingStatus(authentication: Authentication, @PathVariable matchingId: Long, @Valid @RequestBody modifyMatchingStatus: ModifyMatchingStatus): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(modifyMatchingStatus.status != "cancel" && modifyMatchingStatus.status != "complete"){
            throw BaseException(MATCHING_STATUS_INVALID)
        }

        var status = MatchingStatus.CANCEL
        if(modifyMatchingStatus.status == "complete") {
            status = MatchingStatus.COMPLETE
        }

        matchingService.updateMatchingStatus(user.id!!, matchingId, status)

        return BaseResponse(SUCCESS)
    }
}