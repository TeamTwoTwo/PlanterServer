package com.twotwo.planter.manager.service

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import com.twotwo.planter.manager.repository.PlantManagerRepository
import org.springframework.stereotype.Service

@Service
class PlantManagerService(private val plantManagerepository: PlantManagerRepository) {
    fun getPlantManagerList(category: List<PlantManagerCategory>, sort: Int, isPhoto: Boolean, latitude: Double, longitude: Double): List<PlantManager> {
        return plantManagerepository.findAll()
    }

    fun createPlantManagerList(
        plantManager: PlantManager
    ) {
        plantManagerepository.save(plantManager)
    }

}