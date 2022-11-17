package com.twotwo.planter.manager.controller

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.domain.PlantManagerStatus
import com.twotwo.planter.manager.dto.*
import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.data.domain.Pageable
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import kotlin.math.min

@RestController
@RequestMapping("/plant-managers")
class PlantManagerController(private val plantManagerService: PlantManagerService, private val userService: UserService) {
    val categoryEnumList = arrayListOf(PlantManagerCategory.HOUSE, PlantManagerCategory.FLORIST, PlantManagerCategory.EXPERT, PlantManagerCategory.SERVICE)

    @GetMapping("")
    fun getPlantManagerList(authentication: Authentication, @RequestParam(required = false) category: List<Int>?,
                            @RequestParam(required = false, defaultValue = "0") sort: Int,
                            @RequestParam(required = false, defaultValue = "false") isPhoto: Boolean,
                            @RequestParam("page", defaultValue = "0") page: Int,
                            @RequestParam("size", defaultValue = "50") size: Int, pageable: Pageable): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val categoryList = arrayListOf<PlantManagerCategory>()

        if(category !== null){
            for (item in category) {
                if(item < 0 || item >= 4) {
                    throw BaseException(BaseResponseCode.CATEGORY_VALUE_INVALID)
                }
                categoryList.add(categoryEnumList[item])
            }
        }

        if(sort < 0 || sort > 1) {
            throw BaseException(BaseResponseCode.SORT_VALUE_INVALID)
        }

        val plantMangers: List<GetPlantManagerListRes?> = plantManagerService.getPlantManagerList(categoryList, sort, isPhoto, user.latitude!!, user.longitude!!, page, size)

        return BaseResponse(plantMangers)
    }

    @GetMapping("/{plantManagerId}")
    fun getPlantManager(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<GetPlantManagerRes> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val plantManager: PlantManager = plantManagerService.getPlantManager(plantManagerId)
        val images = arrayListOf<String>("https://baris-bucket.s3.ap-northeast-2.amazonaws.com/images.jpeg", "https://baris-bucket.s3.ap-northeast-2.amazonaws.com/images.jpeg")

       /* if(plantManager.images != null) {
            for (item in plantManager.images!!) {
                images.add(item.imageUrl)
            }
        }*/
        val response = GetPlantManagerRes(plantManager.id!!, plantManager.name, categoryEnumList.indexOf(plantManager.category), plantManager.profileImg,
            1.1, plantManager.isPhoto, 3.5, plantManager.description, plantManager.caringPrice, plantManager.pruningPrice,
            images, plantManager.introduction, plantManager.address, plantManager.latitude, plantManager.longitude,
            null, 4)

        return BaseResponse(response)
    }

    @PostMapping("")
    fun updatePlantManagerMatchingActive(authentication: Authentication, @RequestBody updatePlantManagerMatchingActive: UpdatePlantManagerMatchingActive): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val response = plantManagerService.activePlantManager(user, updatePlantManagerMatchingActive.isActive)

        return BaseResponse(response)
    }

    @GetMapping("/{plantManagerId}/option")
    fun getPlantManagerCareOption(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<List<GetPlantManagerOptionRes>> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val response = plantManagerService.getPlantCareOption(plantManagerId)

        return BaseResponse(response)
    }
}