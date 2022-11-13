package com.twotwo.planter.report.repository

import com.twotwo.planter.report.domain.Report
import com.twotwo.planter.review.domain.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportRepository: JpaRepository<Report, Long> {
}