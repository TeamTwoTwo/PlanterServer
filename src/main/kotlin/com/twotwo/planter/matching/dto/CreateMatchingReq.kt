package com.twotwo.planter.matching.dto

import org.hibernate.validator.constraints.Range
import java.time.LocalDate

data class CreateMatchingReq (

    val plantManagerId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,

    @field: Range(min=0, max=1, message = "2010:pickUpType은 0 또는 1 이어야합니다")
    val pickUpType: Int,
    val service: List<PlantToCare>
        )