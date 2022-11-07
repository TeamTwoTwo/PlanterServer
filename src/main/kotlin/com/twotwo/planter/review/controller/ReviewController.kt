package com.twotwo.planter.review.controller

import com.twotwo.planter.common.service.AwsS3Service
import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.review.domain.ReviewImg
import com.twotwo.planter.review.dto.GetReviewListRes
import com.twotwo.planter.review.dto.WriteReviewReq
import com.twotwo.planter.review.dto.WriteReviewRes
import com.twotwo.planter.review.service.ReviewImgService
import com.twotwo.planter.review.service.ReviewService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("")
class ReviewController(private val reviewService: ReviewService, private val reviewImgService: ReviewImgService, private val userService: UserService, private val plantManagerService: PlantManagerService, private val awsS3Service: AwsS3Service) {
    @GetMapping("/plant-managers/{plantManagerId}/reviews")
    fun getReviewList(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<Any> {
        val reviews = reviewService.getReviewList(plantManagerId)
        val response = arrayListOf<GetReviewListRes>()

        for(review in reviews){
            val images = arrayListOf<String>()
            if(review.images !== null){
                for(image in review.images!!){
                    images.add(image!!.imageUrl)
                }
            }
            response.add(GetReviewListRes(review.id!!, review.user.profileImg, review.user.name, review.createdAt!!.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                review.rate, review.contents, images))
        }

        return BaseResponse(response)
    }

    @PostMapping("/reviews")
    fun writeReview(authentication: Authentication, @ModelAttribute writeReviewReq: WriteReviewReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)
        val plantManager = plantManagerService.getPlantManager(writeReviewReq.plantManagerId)

        val review = reviewService.createReview(Review(writeReviewReq.contents, writeReviewReq.rate, user, plantManager))

        if(writeReviewReq.images !== null){
            for(image in writeReviewReq.images){
                val uploadedUrl = awsS3Service.uploadFile("review", image)
                reviewImgService.createReviewImg(ReviewImg(uploadedUrl, review))
            }
        }

        val response = WriteReviewRes(review.id!!)

        return BaseResponse(response)
    }
}