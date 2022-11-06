package com.twotwo.planter.matching.dto

data class GetMatchingListRes (
    val matchingId: Long,
    val plantManagerId: Long,
    val profileImg: String,
    val name: String,
    val category: Int,
    val requestAt: String,
    val status: String
    )