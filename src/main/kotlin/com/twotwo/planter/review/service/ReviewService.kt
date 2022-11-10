package com.twotwo.planter.review.service

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.review.repository.ReviewRepository
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
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

    fun getReviewById(reviewId: Long): Review? {
        val review = reviewRepository.findReviewById(reviewId)

        if(review == null){
            throw BaseException(REVIEW_NOT_FOUND)
        }

        return review
    }

}
