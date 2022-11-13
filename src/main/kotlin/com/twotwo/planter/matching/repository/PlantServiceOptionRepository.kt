package com.twotwo.planter.matching.repository

import com.twotwo.planter.matching.domain.PlantServiceOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlantServiceOptionRepository: JpaRepository<PlantServiceOption, Long> {
}