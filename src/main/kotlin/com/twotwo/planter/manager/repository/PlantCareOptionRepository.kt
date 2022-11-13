package com.twotwo.planter.manager.repository

import com.twotwo.planter.manager.domain.PlantCareOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlantCareOptionRepository: JpaRepository<PlantCareOption, Long> {
    fun findPlantCareOptionById(plantCareOptionId: Long): PlantCareOption?
}