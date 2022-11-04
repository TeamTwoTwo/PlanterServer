package com.twotwo.planter.review.domain

import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class ReviewImg(imageUrl: String, review: Review): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_img_id")
    var id: Long? = null

    var imageUrl: String = imageUrl

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    var review: Review = review
}