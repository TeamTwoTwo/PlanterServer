package com.twotwo.planter.report.service

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.message.service.MessageService
import com.twotwo.planter.report.repository.ReportRepository
import com.twotwo.planter.review.service.ReviewService
import com.twotwo.planter.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReportService(private val reportRepository: ReportRepository, private val reviewService: ReviewService, private val messageService: MessageService, private val plantManagerService: PlantManagerService) {
    fun createReport(user: User, reviewId: Long?, messageId: Long?, plantManagerId: Long?){
        /*if(reviewId !== null && reviewService.getReviewById(reviewId)){

        }*/
    }
}