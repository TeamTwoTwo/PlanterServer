package com.twotwo.planter.review.repository

import com.twotwo.planter.review.domain.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
    @Query(value="SELECT * FROM review r\n" +
            "JOIN matching m ON m.matching_id = r.matching_id\n" +
            "JOIN plant_manager pm ON m.plant_manager_id = pm.plant_manager_id\n" +
            "WHERE pm.plant_manager_id = :plantManagerId\n" +
            "ORDER BY r.created_at DESC\n" +
            "LIMIT :size OFFSET :page*:size", nativeQuery = true)
    fun findAllByPlantManagerId(plantManagerId: Long, page: Int?, size: Int?): List<Review>
    fun findReviewById(reviewId: Long): Review?
}