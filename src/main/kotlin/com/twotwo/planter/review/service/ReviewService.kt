package com.twotwo.planter.review.service

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.review.dto.GetReviewListRes
import com.twotwo.planter.review.repository.ReviewRepository
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
@Transactional(readOnly = true)
class ReviewService(private val reviewRepository: ReviewRepository, private val plantManagerService: PlantManagerService) {
    fun getReviewList(plantManagerId: Long, page: Int?, size: Int?): Any {
        val plantManager = plantManagerService.getPlantManager(plantManagerId)

        val reviews = reviewRepository.findAllByPlantManagerId(plantManagerId, page, size)
        val response = arrayListOf<GetReviewListRes>()

        for(review in reviews){
            val images = arrayListOf<String>()
            if(review.images !== null){
                for(image in review.images!!){
                    images.add(image!!.imageUrl)
                }
            }
            response.add(
                GetReviewListRes(review.id!!, review.matching.user.profileImg, review.matching.user.name, review.createdAt!!.format(
                    DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                review.rate, review.contents, images)
            )
        }
        return response
    }

    @Transactional
    fun createReview(review: Review): Review {
        val insertedReview = reviewRepository.save(review)
        return insertedReview
    }

    fun getReviewById(reviewId: Long): Review {
        val review = reviewRepository.findReviewById(reviewId)

        if(review == null){
            throw BaseException(REVIEW_NOT_FOUND)
        }

        return review
    }

}
