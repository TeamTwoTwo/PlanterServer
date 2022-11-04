package com.twotwo.planter.review.service

import com.twotwo.planter.review.domain.ReviewImg
import com.twotwo.planter.review.repository.ReviewImgRepository
import org.springframework.stereotype.Service


@Service
class ReviewImgService(private val reviewImgRepository: ReviewImgRepository) {
    fun createReviewImg(reviewImg: ReviewImg): Long {
        reviewImgRepository.save(reviewImg)
        return reviewImg.id!!
    }
}
