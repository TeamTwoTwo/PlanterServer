package com.twotwo.planter.review.controller

import com.twotwo.planter.common.service.AwsS3Service
import com.twotwo.planter.matching.service.MatchingService
import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.review.domain.ReviewImg
import com.twotwo.planter.review.dto.GetReviewListRes
import com.twotwo.planter.review.dto.WriteReviewReq
import com.twotwo.planter.review.dto.WriteReviewRes
import com.twotwo.planter.review.service.ReviewImgService
import com.twotwo.planter.review.service.ReviewService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("")
class ReviewController(private val reviewService: ReviewService, private val reviewImgService: ReviewImgService, private val userService: UserService, private val matchingService: MatchingService, private val awsS3Service: AwsS3Service) {
    @GetMapping("/plant-managers/{plantManagerId}/reviews")
    fun getReviewList(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<Any> {
        val response = reviewService.getReviewList(plantManagerId)

        return BaseResponse(response)
    }

    @PostMapping("/reviews")
    fun writeReview(authentication: Authentication, @ModelAttribute writeReviewReq: WriteReviewReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)
        val matching = matchingService.getMatchingById(writeReviewReq.matchingId)

        if(matching.review != null){
            throw BaseException(REVIEW_ALREADY_EXIST)
        }

        val review = reviewService.createReview(Review(writeReviewReq.contents, writeReviewReq.rate, matching))

        if(writeReviewReq.images !== null){
            for(image in writeReviewReq.images){
                val uploadedUrl = awsS3Service.uploadFile("review", image)
                reviewImgService.createReviewImg(ReviewImg(uploadedUrl, review))
            }
        }

        val response = WriteReviewRes(review.id!!, review.matching.plantManager.id!!)

        return BaseResponse(response)
    }
}