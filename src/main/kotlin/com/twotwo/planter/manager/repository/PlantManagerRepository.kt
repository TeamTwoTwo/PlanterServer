package com.twotwo.planter.manager.repository

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlantManagerRepository: JpaRepository<PlantManager, Long> {
    //@Query(value = "SELECT * FROM plant_manager WHERE IF(:isPhoto, is_photo = true, true)", nativeQuery = true)
    fun findOneById(plantManagerId: Long): PlantManager?
    @Query(value = "SELECT *, (\n" +
            "       6371 * acos ( cos ( radians(:longitude))\n" +
            "          * cos( radians( latitude ))\n" +
            "          * cos( radians( longitude) - radians(:latitude))\n" +
            "          + sin ( radians(:longitude)) * sin( radians(latitude) )\n" +
            "       )\n" +
            "   )/1000 AS distance FROM plant_manager WHERE (NOT(:isPhoto) OR is_photo = true)\n" +
            "ORDER BY distance", nativeQuery = true)
    fun findPlantManagers(latitude: Double, longitude: Double, isPhoto: Boolean): List<PlantManager?>
}