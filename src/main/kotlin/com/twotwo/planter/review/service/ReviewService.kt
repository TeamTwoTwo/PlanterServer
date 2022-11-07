package com.twotwo.planter.review.service

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.review.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository, private val plantManagerService: PlantManagerService) {
    fun getReviewList(plantManagerId: Long): List<Review> {
        val plantManager = plantManagerService.getPlantManager(plantManagerId)
        return reviewRepository.findAllByPlantManagerId(plantManagerId)
    }

    fun createReview(review: Review): Review {
        val insertedReview = reviewRepository.save(review)
        return insertedReview
    }
}
