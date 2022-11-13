package com.twotwo.planter.matching.service

import com.twotwo.planter.manager.repository.PlantCareOptionRepository
import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.manager.util.PlantManagerUtil
import com.twotwo.planter.matching.domain.*
import com.twotwo.planter.matching.dto.GetMatchingDetailRes
import com.twotwo.planter.matching.dto.PlantServiceRes
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
import java.time.temporal.ChronoUnit

@Service
@Transactional(readOnly = true)
class MatchingService(private val matchingRepository: MatchingRepository, private val plantManagerService: PlantManagerService, private val plantServiceRepository: PlantServiceRepository, private val plantCareOptionRepository: PlantCareOptionRepository, private val plantServiceOptionRepository: PlantServiceOptionRepository, private val plantManagerUtil: PlantManagerUtil) {
    fun getMatchingList(userId: Long): List<Matching> {
        return matchingRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
    }

    fun getMatchingDetail(matchingId: Long): Any {
        val matching = matchingRepository.findMatchingById(matchingId)
        if(matching == null){
            throw BaseException(MATCHING_NOT_FOUND)
        }
        val service = arrayListOf<PlantServiceRes>()
        var totalPrice = 0

        for(plant in matching.plants){
            service.add(PlantServiceRes(plant.name, plant.count, plant.plantServiceOption[0].plantCareOption.price, plant.plantServiceOption[0].plantCareOption.name))
            totalPrice += plant.plantServiceOption[0].plantCareOption.price * plant.count
        }

        val response = GetMatchingDetailRes(matching.id!!, matching.plantManager.id!!, matching.plantManager.profileImg, matching.plantManager.name,
            plantManagerUtil.getCategoryInt(matching.plantManager.category),
            matching.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), matching.status.toString().lowercase(),
            service, totalPrice, matching.startDate.format(DateTimeFormatter.ofPattern("MM.dd")), matching.endDate.format(DateTimeFormatter.ofPattern("MM.dd")),
            ChronoUnit.DAYS.between(matching.startDate, matching.endDate) + 1, matching.pickUpType.ordinal, matching.review?.id
        )
        return response
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
            val plantService = plantServiceRepository.save(PlantService(plant.plantName, 1, matching))
            for(option in plant.optionId) {
                if(option !== null){
                    val plantCareOption = plantCareOptionRepository.findPlantCareOptionById(option)
                    if(plantCareOption === null){
                        throw BaseException(PLANT_CARE_OPTION_NOT_FOUND)
                    }
                    val plantServiceOption = plantServiceOptionRepository.save(PlantServiceOption(plantService, plantCareOption))
                }
            }
        }
        return 1
    }

    /*@Transactional
    fun updateMatchingStatusToDelete(userId: Long, plantManagerId: Long): Int {
        matchingRepository.updateStatus(userId, plantManagerId)
        return 1
    }*/
}