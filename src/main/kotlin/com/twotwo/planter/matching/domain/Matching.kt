package com.twotwo.planter.matching.domain

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseTime
import java.time.LocalDate
import javax.persistence.*

@Entity
class Matching(status: MatchingStatus, startDate: LocalDate, endDate: LocalDate, pickUpType: PickUpType, user: User, plantManager: PlantManager): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    var id: Long? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: MatchingStatus = status

    @Column(nullable = false)
    var startDate: LocalDate = startDate

    @Column(nullable = false)
    var endDate: LocalDate = endDate

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var pickUpType: PickUpType = pickUpType

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "matching", cascade = [CascadeType.ALL])
    var plants: List<PlantService> = arrayListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_manager_id")
    var plantManager: PlantManager = plantManager

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "matching", cascade = [CascadeType.ALL])
    var review: Review? = null
}