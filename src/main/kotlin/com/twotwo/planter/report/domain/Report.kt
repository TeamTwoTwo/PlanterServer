package com.twotwo.planter.report.domain

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.message.domain.Message
import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseTime

import javax.persistence.*


@Entity
class Report(user: User, review: Review?, message: Message?, plantManager: PlantManager?): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    var review: Review? = review

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    var message: Message? = message

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_manager_id")
    var plantManager: PlantManager? = plantManager
}