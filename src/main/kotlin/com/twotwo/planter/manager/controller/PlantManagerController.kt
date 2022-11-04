package com.twotwo.planter.manager.controller

import com.twotwo.planter.auth.service.AuthService
import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.dto.CreatePlantManagerReq
import com.twotwo.planter.manager.dto.GetPlantManagerListRes
import com.twotwo.planter.manager.dto.GetPlantManagerRes
import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plant-managers")
class PlantManagerController(private val plantManagerService: PlantManagerService, private val userService: UserService) {
    val categoryEnumList = arrayListOf(PlantManagerCategory.HOUSE, PlantManagerCategory.FLORIST, PlantManagerCategory.EXPERT, PlantManagerCategory.SERVICE)

    @GetMapping("")
    fun getPlantManagerList(authentication: Authentication, @RequestParam(required = false, defaultValue = "0") category: List<Int>,
                            @RequestParam(required = false, defaultValue = "0") sort: Int,
                            @RequestParam(required = false, defaultValue = "false") isPhoto: Boolean,
                            @RequestParam(required = false, defaultValue = "0") latitude: Double,
                            @RequestParam(required = false, defaultValue = "0") longitude: Double): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        println(userDetails.username)
        val user = userService.findUser(userDetails.username)
        println(user.id)

        val categoryList = arrayListOf<PlantManagerCategory>()
        for (item in category) {
            categoryList.add(categoryEnumList[item])
            if(item < 0 || item >= 4) {
                throw BaseException(BaseResponseCode.CATEGORY_VALUE_INVAID)
            }
        }
        if(sort < 0 || sort > 1) {
            throw BaseException(BaseResponseCode.SORT_VALUE_INVALID)
        }

        val plantMangers: List<PlantManager> = plantManagerService.getPlantManagerList(categoryList, sort, isPhoto, latitude, longitude)
        val response = arrayListOf<GetPlantManagerListRes>()

        for(item in plantMangers){
            response.add(GetPlantManagerListRes(item.id!!, item.name, categoryEnumList.indexOf(item.category), item.profileImg, 11.1, item.isPhoto, 4.5, item.description, item.caringPrice))
        }

        return BaseResponse(response)
    }

    @GetMapping("/{plantManagerId}")
    fun getPlantManager(@PathVariable plantManagerId: Long): BaseResponse<GetPlantManagerRes> {
        //val userDetails: UserDetails = authentication.principal as UserDetails
        //println(userDetails.username)
        //val user = authService.findUser(userDetails.username)

        val plantManager: PlantManager = plantManagerService.getPlantManager(plantManagerId)
        val images = arrayListOf<String>("https://baris-bucket.s3.ap-northeast-2.amazonaws.com/images.jpeg", "https://baris-bucket.s3.ap-northeast-2.amazonaws.com/images.jpeg")

       /* if(plantManager.images != null) {
            for (item in plantManager.images!!) {
                images.add(item.imageUrl)
            }
        }*/
        val response = GetPlantManagerRes(plantManager.id!!, plantManager.name, categoryEnumList.indexOf(plantManager.category), plantManager.profileImg,
            123.5, plantManager.isPhoto, 5.5, plantManager.description, plantManager.caringPrice, plantManager.pruningPrice,
            images, plantManager.introduction, plantManager.address, plantManager.latitude, plantManager.longitude,
            null, 12345)

        return BaseResponse(response)
    }

    @PostMapping("")
    fun createPlantManager(@RequestBody createPlantManagerReq: CreatePlantManagerReq): BaseResponse<Any> {

        val plantManager = PlantManager(createPlantManagerReq.name, createPlantManagerReq.profileImg, createPlantManagerReq.description,
            createPlantManagerReq.caringPrice, createPlantManagerReq.pruningPrice,
            createPlantManagerReq.address, createPlantManagerReq.latitude, createPlantManagerReq.longitude,
            createPlantManagerReq.is_photo, createPlantManagerReq.category, createPlantManagerReq.introduction)

        plantManagerService.createPlantManagerList(plantManager)

        return BaseResponse(BaseResponseCode.SUCCESS)
    }
}