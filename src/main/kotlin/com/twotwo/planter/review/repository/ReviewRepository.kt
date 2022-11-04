package com.twotwo.planter.review.repository

import com.twotwo.planter.review.domain.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
}