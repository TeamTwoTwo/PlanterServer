package com.twotwo.planter.report.service

import com.twotwo.planter.manager.domain.PlantManagerStatus
import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.message.service.MessageService
import com.twotwo.planter.report.domain.Report
import com.twotwo.planter.report.repository.ReportRepository
import com.twotwo.planter.review.service.ReviewService
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReportService(private val reportRepository: ReportRepository, private val reviewService: ReviewService, private val messageService: MessageService, private val plantManagerService: PlantManagerService) {
    @Transactional
    fun createReport(user: User, reviewId: Long?, messageId: Long?, plantManagerId: Long?): Long{
        var reportId: Long = 0
        if(reviewId !== null){
            val review = reviewService.getReviewById(reviewId)
            val report = Report(user, review, null, null)
            val insertedReport = reportRepository.save(report)
            reportId = insertedReport.id!!
        }
        if(messageId !== null){
            val message = messageService.getMessageById(messageId)
            val report = Report(user, null, message, null)
            val insertedReport = reportRepository.save(report)
            reportId = insertedReport.id!!
        }
        if(plantManagerId !== null){
            val plantManager = plantManagerService.getPlantManager(plantManagerId)
            val report = Report(user, null, null, plantManager)
            val insertedReport = reportRepository.save(report)
            reportId = insertedReport.id!!

            plantManagerService.blockPlantManager(plantManager)
        }
        return reportId
    }
}