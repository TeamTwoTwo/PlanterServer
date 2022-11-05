package com.twotwo.planter.matching.service

import com.twotwo.planter.matching.domain.Matching
import com.twotwo.planter.matching.repository.MatchingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MatchingService(private val matchingRepository: MatchingRepository) {
    fun getMatchingList(userId: Long): List<Matching> {
        return matchingRepository.findAllByUserId(userId)
    }

    /*@Transactional
    fun createMatching(matching: Matching): Matching {
        val insertedMatching = matchingRepository.save(matching)
        return insertedMatching
    }

    fun getMatchingDetail(userId: Long, plantManagerId: Long): List<Matching> {
        return matchingRepository.findAllByUserIdAndPlantManagerIdOrderByCreatedAtDesc(userId, plantManagerId)
    }

    @Transactional
    fun updateMatchingStatusToDelete(userId: Long, plantManagerId: Long): Int {
        matchingRepository.updateStatus(userId, plantManagerId)
        return 1
    }*/
}