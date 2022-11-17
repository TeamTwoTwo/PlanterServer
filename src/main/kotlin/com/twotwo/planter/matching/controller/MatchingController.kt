package com.twotwo.planter.matching.controller

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.matching.domain.Matching
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
    fun getMatchingList(authentication: Authentication, @RequestParam("type", defaultValue = "0") type: Int): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        var matchings: List<Matching>
        val response = arrayListOf<GetMatchingListRes>()

        //recieved matching
        if(type == 1){
            if(user.plantManager === null){
                throw BaseException(USER_PLANTMANAGER_NOT_FOUND)
            }
            matchings = matchingService.getPlantManagerMatchingList(user.plantManager!!.id!!)
            for (matching in matchings) {
                response.add(
                    GetMatchingListRes(
                        matching.id!!,
                        matching.user.id!!,
                        matching.plantManager.id!!,
                        matching.user.profileImg,
                        matching.user.name,
                        0,
                        matching.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                        matching.status.toString().lowercase()
                    )
                )
            }
        }
        else {
            matchings = matchingService.getMatchingList(user.id!!)
            for (matching in matchings) {
                response.add(
                    GetMatchingListRes(
                        matching.id!!,
                        matching.user.id!!,
                        matching.plantManager.id!!,
                        matching.plantManager.profileImg,
                        matching.plantManager.name,
                        plantManagerUtil.getCategoryInt(matching.plantManager.category),
                        matching.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                        matching.status.toString().lowercase()
                    )
                )
            }
        }

        return BaseResponse(response)
    }

    @GetMapping("/matchings/{matchingId}")
    fun getMatchingDetail(authentication: Authentication, @PathVariable matchingId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val response = matchingService.getMatchingDetail(matchingId)

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