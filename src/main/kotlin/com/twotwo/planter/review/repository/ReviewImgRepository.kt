package com.twotwo.planter.review.repository

import com.twotwo.planter.review.domain.ReviewImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewImgRepository: JpaRepository<ReviewImg, Long> {
}