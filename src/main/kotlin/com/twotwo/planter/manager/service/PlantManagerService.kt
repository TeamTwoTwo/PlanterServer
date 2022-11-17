package com.twotwo.planter.manager.service

import com.twotwo.planter.manager.domain.PlantCareOption
import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.domain.PlantManagerStatus
import com.twotwo.planter.manager.dto.GetPlantManagerListRes
import com.twotwo.planter.manager.dto.GetPlantManagerOptionRes
import com.twotwo.planter.manager.dto.UpdatePlantManagerMatchingActiveRes
import com.twotwo.planter.manager.repository.PlantCareOptionRepository
import com.twotwo.planter.manager.repository.PlantManagerRepository
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.min

@Service
@Transactional(readOnly = true)
class PlantManagerService(private val plantManagerRepository: PlantManagerRepository, private val plantCareOptionRepository: PlantCareOptionRepository, private val plantManagerUtil: PlantManagerUtil) {
    fun getPlantManagerList(category: List<PlantManagerCategory>, sort: Int, isPhoto: Boolean, latitude: Double, longitude: Double, page: Int, size: Int): List<GetPlantManagerListRes?> {
        val categoryEnumList = arrayListOf(PlantManagerCategory.HOUSE, PlantManagerCategory.FLORIST, PlantManagerCategory.EXPERT, PlantManagerCategory.SERVICE)
        val filteredManagers: List<PlantManager> = plantManagerRepository.findPlantManagers(isPhoto, page, size)

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

        if(sort == 1){
            response.reverse()
        }
        return response
    }

    fun getPlantManager(plantManagerId: Long): PlantManager {
        val plantManager = plantManagerRepository.findOneById(plantManagerId)

        if(plantManager == null){
            throw BaseException(PLANTER_MANAGER_NOT_FOUND)
        }
        return plantManager
    }

    @Transactional
    fun createPlantManagerList(
        plantManager: PlantManager
    ) {
        plantManagerRepository.save(plantManager)
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
        plantManagerRepository.save(plantManager)
    }

    @Transactional
    fun activePlantManager(user: User, isActive: Boolean): UpdatePlantManagerMatchingActiveRes {
        val plantManager = user.plantManager

        // new plantmanager
        if(plantManager === null && isActive){
            val newPlantManager = PlantManager(user.name, "", "",
                2000, 3000,
                user.address, user.latitude!!, user.longitude!!,
                true, PlantManagerCategory.HOUSE, "", PlantManagerStatus.ACTIVE, user)
            plantManagerRepository.save(newPlantManager)
            val option = PlantCareOption("식물관리", 1000, newPlantManager)
            plantCareOptionRepository.save(option)

            return UpdatePlantManagerMatchingActiveRes(user.id!!, newPlantManager.id!!,true)
        }
        else if (plantManager !== null) {
            if(isActive){
                plantManager.status = PlantManagerStatus.ACTIVE
                plantManagerRepository.save(plantManager)
            }else {
                plantManager.status = PlantManagerStatus.INACTIVE
                plantManagerRepository.save(plantManager)
            }
        }
        else { // user.plantManager === null && !isActive
            throw BaseException(USER_PLANTMANAGER_NOT_FOUND)
        }
        return UpdatePlantManagerMatchingActiveRes(user.id!!, plantManager?.id!!, plantManager.status === PlantManagerStatus.ACTIVE)
    }
}