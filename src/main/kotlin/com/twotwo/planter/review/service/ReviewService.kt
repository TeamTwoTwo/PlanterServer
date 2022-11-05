package com.twotwo.planter.review.service

import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.review.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {
    fun getReviewList(): List<Review> {
        return reviewRepository.findAll()
    }

    fun createReview(review: Review): Review {
        val insertedReview = reviewRepository.save(review)
        return insertedReview
    }
}
