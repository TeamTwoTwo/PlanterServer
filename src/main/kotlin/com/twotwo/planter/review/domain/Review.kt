package com.twotwo.planter.review.domain

import com.twotwo.planter.manager.domain.ManagerImg
import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class Review(contents: String, rate: Double, user: User, plantManager: PlantManager): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    var id: Long? = null

    @Column(nullable = false)
    var contents: String = contents
    var rate: Double = rate

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_manager_id")
    var plantManager: PlantManager = plantManager

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "review")
    var images: List<ReviewImg?>? = ArrayList<ReviewImg>()
}