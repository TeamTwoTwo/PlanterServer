package com.twotwo.planter.report.controller

import com.twotwo.planter.report.dto.CreateReportReq
import com.twotwo.planter.report.dto.CreateReportRes
import com.twotwo.planter.report.service.ReportService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/reports")
class ReportController(private val reportService: ReportService, private val userService: UserService){
    @PostMapping("")
    fun getMessageList(authentication: Authentication, @RequestBody createReportReq: CreateReportReq): BaseResponse<CreateReportRes> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        if(createReportReq.reviewId === null && createReportReq.messageId === null && createReportReq.plantManagerId === null){
            throw BaseException(REPORT_REQUEST_VALUE_EMPTY)
        }

        val reportId = reportService.createReport(user, createReportReq.reviewId, createReportReq.messageId, createReportReq.plantManagerId)

        return BaseResponse(CreateReportRes(reportId))
    }
}