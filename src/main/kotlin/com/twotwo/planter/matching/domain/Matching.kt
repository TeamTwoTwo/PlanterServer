package com.twotwo.planter.matching.domain

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.message.domain.MessageImg
import com.twotwo.planter.message.domain.MessageStatus
import com.twotwo.planter.message.domain.SenderType
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseTime
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "matching")
    var plants: List<PlantService> = arrayListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_manager_id")
    var plantManager: PlantManager = plantManager
}