package com.twotwo.planter.matching.controller

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.matching.domain.MatchingStatus
import com.twotwo.planter.matching.domain.PickUpType
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
            service.add(PlantServiceRes(plant.name, plant.count, plant.plantServiceOption[0].plantCareOption.price, plant.plantServiceOption[0].plantCareOption.name))
            totalPrice += plant.plantServiceOption[0].plantCareOption.price * plant.count
        }
        val response = GetMatchingDetailRes(matching.id!!, matching.plantManager.id!!, matching.plantManager.profileImg, matching.plantManager.name,
            plantManagerUtil.getCategoryInt(matching.plantManager.category),
            matching.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), matching.status.toString().lowercase(),
            service, totalPrice, matching.startDate.format(DateTimeFormatter.ofPattern("MM.dd")), matching.endDate.format(DateTimeFormatter.ofPattern("MM.dd")),
            ChronoUnit.DAYS.between(matching.startDate, matching.endDate) + 1, matching.pickUpType.ordinal, matching.review?.id
            )

        return BaseResponse(response)
    }

    @PostMapping("/matchings")
    fun createMatching(authentication: Authentication, @Valid @RequestBody createMatchingReq: CreateMatchingReq): BaseResponse<CreateMatchingRes> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        var pickUpType = PickUpType.USER_GO

        if(createMatchingReq.pickUpType == 1){
            pickUpType = PickUpType.PLANTMANAGER_GO
        }

        val matching = matchingService.createMatching(
            user, createMatchingReq.plantManagerId, createMatchingReq.startDate, createMatchingReq.endDate, pickUpType, arrayListOf()
        )
        matchingService.createPlantService(createMatchingReq.service, matching)

        return BaseResponse(CreateMatchingRes(matching.id!!))
    }

    @PatchMapping("/matchings/{matchingId}")
    fun modifyMatchingStatus(authentication: Authentication, @PathVariable matchingId: Long, @Valid @RequestBody modifyMatchingStatusReq: ModifyMatchingStatusReq): BaseResponse<ModifyMatchingStatusRes> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(modifyMatchingStatusReq.status != "cancel" && modifyMatchingStatusReq.status != "complete"){
            throw BaseException(MATCHING_STATUS_INVALID)
        }

        var status = MatchingStatus.CANCEL
        if(modifyMatchingStatusReq.status == "complete") {
            status = MatchingStatus.COMPLETE
        }

        val matching = matchingService.updateMatchingStatus(user.id!!, matchingId, status)

        return BaseResponse(ModifyMatchingStatusRes(matching.id!!, matching.status.toString().lowercase()))
    }
}