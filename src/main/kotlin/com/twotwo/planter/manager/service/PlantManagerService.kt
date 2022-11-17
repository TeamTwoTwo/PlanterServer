package com.twotwo.planter.manager.service

import com.twotwo.planter.manager.domain.PlantCareOption
import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.domain.PlantManagerStatus
import com.twotwo.planter.manager.dto.*
import com.twotwo.planter.manager.repository.PlantCareOptionRepository
import com.twotwo.planter.manager.repository.PlantManagerRepository
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
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
    fun updatePlantManager(user: User, isActive: Boolean, images: List<MultipartFile>?, introduction: String?, description: String?, price: Int, isPhoto: Boolean): UpdatePlantManagerRes {
        var plantManager = user.plantManager
        // TODO: 서비스 옵션 저장
        // TODO: 이미지 저장
        var plantManagerId: Long = if(user.plantManager !== null) user.plantManager!!.id!! else 0

        // new plant manager
        if(plantManager === null && isActive){
            val newPlantManager = PlantManager(user.name, "", description,
                2000, 3000,
                user.address, user.latitude!!, user.longitude!!,
                isPhoto, PlantManagerCategory.HOUSE, introduction, PlantManagerStatus.ACTIVE, user)
            val insertedPlantManager = plantManagerRepository.save(newPlantManager)
            val option = PlantCareOption("식물관리", price, newPlantManager)
            plantCareOptionRepository.save(option)
            plantManagerId = insertedPlantManager.id!!
        }
        else if (plantManager !== null) {
            plantManager.status = if(isActive) PlantManagerStatus.ACTIVE else  PlantManagerStatus.INACTIVE
            plantManager.introduction = introduction
            plantManager.description = description
            plantManager.isPhoto = isPhoto

            plantManagerRepository.save(plantManager)
        }
        else { // user.plantManager === null && !isActive
            throw BaseException(USER_PLANTMANAGER_NOT_FOUND)
        }

        val _plantManager = getPlantManager(plantManagerId)
        val careOptions = arrayListOf<CareOption>()
        for(item in _plantManager.plantCares!!){
            careOptions.add(CareOption(item!!.id!!, item.name, item.price))
        }
        return UpdatePlantManagerRes(_plantManager.id!!, _plantManager.status === PlantManagerStatus.ACTIVE, arrayListOf(),
            _plantManager.description, _plantManager.introduction, careOptions, _plantManager.isPhoto)
    }

    @Transactional
    fun getPlantManagerByUser(user: User): GetPlantManagerEditPageRes {

        if(user.plantManager === null){
            throw BaseException(USER_PLANTMANAGER_NOT_FOUND)
        }

        val plantManager = getPlantManager(user.plantManager!!.id!!)

        val careOptions = arrayListOf<CareOption>()
            for(item in plantManager.plantCares){
                careOptions.add(CareOption(item!!.id!!, item.name, item.price))
            }


        val response = GetPlantManagerEditPageRes(plantManager.id!!, plantManager.status === PlantManagerStatus.ACTIVE, arrayListOf("https://baris-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3+(3).jpeg"), plantManager.introduction, plantManager.description, careOptions, plantManager.isPhoto)

        return response
    }
}