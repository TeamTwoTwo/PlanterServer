package com.twotwo.planter.matching.service

import com.twotwo.planter.manager.repository.PlantCareOptionRepository
import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.matching.domain.*
import com.twotwo.planter.matching.dto.PlantToCare
import com.twotwo.planter.matching.repository.MatchingRepository
import com.twotwo.planter.matching.repository.PlantServiceOptionRepository
import com.twotwo.planter.matching.repository.PlantServiceRepository
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
@Transactional(readOnly = true)
class MatchingService(private val matchingRepository: MatchingRepository, private val plantManagerService: PlantManagerService, private val plantServiceRepository: PlantServiceRepository, private val plantCareOptionRepository: PlantCareOptionRepository, private val plantServiceOptionRepository: PlantServiceOptionRepository) {
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
    fun updateMatchingStatus(userId: Long, matchingId: Long, status: MatchingStatus): Matching {
        val matching = matchingRepository.findMatchingById(matchingId)

        if(matching === null){
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
        val modifiedMatching = matchingRepository.save(matching)
        return modifiedMatching
    }

    fun getMatchingById(matchingId: Long): Matching {
        val matching = matchingRepository.findMatchingById(matchingId)

        if(matching === null){
            throw BaseException(MATCHING_NOT_FOUND)
        }
        return matching
    }


    @Transactional
    fun createMatching(user: User, plantManagerId: Long, startDate: LocalDate, endDate: LocalDate, pickUpType: PickUpType, plants: List<PlantToCare>): Matching {
        val plantManager = plantManagerService.getPlantManager(plantManagerId)
        val matching = Matching(MatchingStatus.REQUEST, startDate, endDate, pickUpType, user, plantManager)

        val insertedMatching = matchingRepository.save(matching)
        return insertedMatching
    }

    @Transactional
    fun createPlantService(plants: List<PlantToCare>, matching: Matching): Int {
        for(plant in plants){
            val plantCareOption = plantCareOptionRepository.findPlantCareOptionById(plant.optionId)
            if(plantCareOption === null){
                throw BaseException(PLANT_CARE_OPTION_NOT_FOUND)
            }
            val plantService = plantServiceRepository.save(PlantService(plant.plantName, plant.plantCount, matching))
            val plantServiceOption = plantServiceOptionRepository.save(PlantServiceOption(plantService, plantCareOption))
        }
        return 1
    }

    /*@Transactional
    fun updateMatchingStatusToDelete(userId: Long, plantManagerId: Long): Int {
        matchingRepository.updateStatus(userId, plantManagerId)
        return 1
    }*/
}