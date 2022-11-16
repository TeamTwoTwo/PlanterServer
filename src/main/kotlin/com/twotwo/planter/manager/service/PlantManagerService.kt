package com.twotwo.planter.manager.service

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.domain.PlantManagerStatus
import com.twotwo.planter.manager.dto.GetPlantManagerListRes
import com.twotwo.planter.manager.dto.GetPlantManagerOptionRes
import com.twotwo.planter.manager.repository.PlantManagerRepository
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.min

@Service
@Transactional(readOnly = true)
class PlantManagerService(private val plantManagerepository: PlantManagerRepository, private val plantManagerUtil: PlantManagerUtil) {
    fun getPlantManagerList(category: List<PlantManagerCategory>, sort: Int, isPhoto: Boolean, latitude: Double, longitude: Double, page: Int, size: Int): List<GetPlantManagerListRes?> {
        val categoryEnumList = arrayListOf(PlantManagerCategory.HOUSE, PlantManagerCategory.FLORIST, PlantManagerCategory.EXPERT, PlantManagerCategory.SERVICE)
        val filteredManagers: List<PlantManager> = plantManagerepository.findPlantManagers(isPhoto, page, size)

        val response = arrayListOf<GetPlantManagerListRes?>()
        for (item in filteredManagers) {
            // category filtering
            if(category.isEmpty() ||  category.indexOf(item.category) != -1) {
                var rateSum: Double = 0.0
                var reviewCount: Int = 0
                if (item.matchings.isNotEmpty()) {
                    for (matching in item.matchings) {
                        if (matching!!.review !== null) {
                            rateSum += matching!!.review!!.rate
                            reviewCount += 1
                        }
                    }
                }

                var rate: Double = rateSum / reviewCount
                if (rate.isNaN()) rate = 0.0

                var minPrice = 0
                if (item.plantCares !== null) {
                    for (option in item.plantCares!!) {
                        if (minPrice != 0) {
                            minPrice = min(option!!.price, minPrice)
                        } else {
                            minPrice = option!!.price
                        }
                    }
                }

                // calculate distance
                val distance = plantManagerUtil.getDistance(latitude, longitude, item.latitude, item.longitude)

                response.add(
                    GetPlantManagerListRes(
                        item.id!!,
                        item.name,
                        categoryEnumList.indexOf(item.category),
                        item.profileImg,
                        distance,
                        item.isPhoto,
                        rate,
                        item.description,
                        minPrice
                    )
                )
            }
        }
        // sort by distance or rate
        var comparator : Comparator<GetPlantManagerListRes?> = compareBy { it!!.distance }

        // sort by rate
        if(sort == 1){
            comparator = compareBy { it!!.rate }
        }
        response.sortWith(comparator)
        response.reverse()
        return response
    }

    fun getPlantManager(plantManagerId: Long): PlantManager {
        val plantManager = plantManagerepository.findOneById(plantManagerId)

        if(plantManager == null){
            throw BaseException(PLANTER_MANAGER_NOT_FOUND)
        }
        return plantManager
    }

    @Transactional
    fun createPlantManagerList(
        plantManager: PlantManager
    ) {
        plantManagerepository.save(plantManager)
    }

    fun getPlantCareOption(plantManagerId: Long): List<GetPlantManagerOptionRes> {
        val plantManager = this.getPlantManager(plantManagerId)

        val response = arrayListOf<GetPlantManagerOptionRes>()

        if(plantManager.plantCares !== null){
            for(item in plantManager.plantCares!!){
                response.add(GetPlantManagerOptionRes(item!!.id!!, item.name, item.price))
            }
        }
        return response
    }

    @Transactional
    fun blockPlantManager(plantManager: PlantManager) {
        plantManager.status = PlantManagerStatus.BLOCKED
        plantManagerepository.save(plantManager)
    }
}