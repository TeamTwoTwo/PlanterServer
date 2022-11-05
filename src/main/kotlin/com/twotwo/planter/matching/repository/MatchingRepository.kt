package com.twotwo.planter.matching.repository

import com.twotwo.planter.matching.domain.Matching
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchingRepository: JpaRepository<Matching, Long> {
    fun findAllByUserId(userId: Long): List<Matching>
}