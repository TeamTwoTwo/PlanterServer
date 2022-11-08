package com.twotwo.planter.matching.service

import com.twotwo.planter.matching.domain.Matching
import com.twotwo.planter.matching.domain.MatchingStatus
import com.twotwo.planter.matching.repository.MatchingRepository
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MatchingService(private val matchingRepository: MatchingRepository) {
    fun getMatchingList(userId: Long): List<Matching> {
        return matchingRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
    }

    fun getMatchingDetail(matchingId: Long): Matching {
        val matching = matchingRepository.findMatchingById(matchingId)
        if(matching == null){
            throw BaseException(MATCHING_NOT_FOUND)
        }
        return matching
    }

    @Transactional
    fun updateMatchingStatus(userId: Long, matchingId: Long, status: MatchingStatus): Int {
        val matching = matchingRepository.findMatchingById(matchingId)

        if(matching == null){
            throw BaseException(MATCHING_NOT_FOUND)
        }
        if(matching.user.id != userId){
            throw BaseException(MATCHING_USER_NOT_MATCH)
        }
        if(status == MatchingStatus.CANCEL && matching.status != MatchingStatus.REQUEST){
            throw BaseException(MATCHING_STATUS_NOT_REQUEST)
        }
        if (status == MatchingStatus.COMPLETE && matching.status != MatchingStatus.CARE) {
            throw BaseException(MATCHING_STATUS_NOT_CARE)
        }

        matching.status = status
        matchingRepository.save(matching)
        return 1
    }

    fun getMatchingById(matchingId: Long): Matching {
        val matching = matchingRepository.findMatchingById(matchingId)

        if(matching === null){
            throw BaseException(MATCHING_NOT_FOUND)
        }
        return matching
    }


    @Transactional
    fun createMatching(matching: Matching): Matching {
        val insertedMatching = matchingRepository.save(matching)
        return insertedMatching
    }

    /*@Transactional
    fun updateMatchingStatusToDelete(userId: Long, plantManagerId: Long): Int {
        matchingRepository.updateStatus(userId, plantManagerId)
        return 1
    }*/
}