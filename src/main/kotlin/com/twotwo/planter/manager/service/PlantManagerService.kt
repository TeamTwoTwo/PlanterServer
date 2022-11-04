package com.twotwo.planter.manager.service

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.repository.PlantManagerRepository
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class PlantManagerService(private val plantManagerepository: PlantManagerRepository) {
    fun getPlantManagerList(category: List<PlantManagerCategory>, sort: Int, isPhoto: Boolean, latitude: Double, longitude: Double): List<PlantManager> {
        return plantManagerepository.findAll()
    }

    fun getPlantManager(plantManagerId: Long): PlantManager {
        val plantManager = plantManagerepository.findOneById(plantManagerId)

        if(plantManager == null){
            throw BaseException(PLANTER_MANAGER_NOT_FOUND)
        }
        return plantManager
    }

    fun createPlantManagerList(
        plantManager: PlantManager
    ) {
        plantManagerepository.save(plantManager)
    }

}