package com.twotwo.planter.matching.repository

import com.twotwo.planter.matching.domain.PlantService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlantServiceRepository: JpaRepository<PlantService, Long> {
}