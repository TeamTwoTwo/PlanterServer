package com.twotwo.planter.manager.repository

import com.twotwo.planter.manager.domain.PlantManager
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlantManagerRepository: JpaRepository<PlantManager, Long> {
    //@Query(value = "SELECT * FROM plant_manager WHERE IF(:isPhoto, is_photo = true, true)", nativeQuery = true)
    fun findOneById(plantManagerId: Long): PlantManager?
    @Query(value = "SELECT *\n" +
            "FROM plant_manager AS PM\n" +
            "WHERE (NOT(:isPhoto) OR is_photo = true)\n" +
            "LIMIT :size OFFSET :page*:size", nativeQuery = true)
    fun findPlantManagers(isPhoto: Boolean, page: Int, size: Int): List<PlantManager>
}