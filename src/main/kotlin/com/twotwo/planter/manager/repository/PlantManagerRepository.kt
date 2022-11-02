package com.twotwo.planter.manager.repository

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.manager.domain.PlantManagerCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlantManagerRepository: JpaRepository<PlantManager, Long> {
    //@Query(value = "SELECT * FROM plant_manager WHERE IF(:isPhoto, is_photo = true, true)", nativeQuery = true)
}