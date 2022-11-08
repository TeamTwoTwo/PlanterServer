package com.twotwo.planter.matching.dto

data class CreateMatchingReq (

            val startDate: String,
            val endDate: String,
            val pickUpType: Int
        )