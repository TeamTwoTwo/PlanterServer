package com.twotwo.planter.manager.controller

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.dto.CreatePlantManagerReq
import com.twotwo.planter.manager.dto.GetPlantManagerListRes
import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/plant-managers")
class PlantManagerController(private val plantManagerService: PlantManagerService) {
    @GetMapping("")
    fun getPlantManagerList(@RequestParam(required = false, defaultValue = "0") category: List<Int>,
                            @RequestParam(required = false, defaultValue = "0") sort: Int,
                            @RequestParam(required = false, defaultValue = "false") isPhoto: Boolean,
                            @RequestParam(required = false, defaultValue = "0") latitude: Double,
                            @RequestParam(required = false, defaultValue = "0") longitude: Double): BaseResponse<Any> {

        val categoryEnumList = arrayListOf(PlantManagerCategory.HOUSE, PlantManagerCategory.FLORIST, PlantManagerCategory.EXPERT, PlantManagerCategory.SERVICE)
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

    @PostMapping("")
    fun getPlantManagerList(@RequestBody createPlantManagerReq: CreatePlantManagerReq): BaseResponse<Any> {

        val plantManager = PlantManager(createPlantManagerReq.name, createPlantManagerReq.profileImg, createPlantManagerReq.description,
            createPlantManagerReq.caringPrice, createPlantManagerReq.pruningPrice,
            createPlantManagerReq.address, createPlantManagerReq.latitude, createPlantManagerReq.longitude,
            createPlantManagerReq.is_photo, createPlantManagerReq.category)

        plantManagerService.createPlantManagerList(plantManager)

        return BaseResponse(BaseResponseCode.SUCCESS)
    }
}