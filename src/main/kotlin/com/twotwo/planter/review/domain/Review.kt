package com.twotwo.planter.review.domain

import com.twotwo.planter.matching.domain.Matching
import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class Review(contents: String, rate: Double, matching: Matching): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    var id: Long? = null

    @Column(nullable = false)
    var contents: String = contents
    var rate: Double = rate

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    var matching: Matching = matching

    @OneToMany(mappedBy = "review")
    var images: List<ReviewImg?>? = ArrayList<ReviewImg>()
}