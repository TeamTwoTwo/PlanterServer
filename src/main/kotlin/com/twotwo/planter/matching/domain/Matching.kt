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
class Matching(status: MatchingStatus, totalPrice: Int, date: LocalDate, user: User, plantManager: PlantManager): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    var id: Long? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: MatchingStatus = status

    @Column(nullable = false)
    var totalPrice: Int = totalPrice

    @Column(nullable = false)
    var date: LocalDate = date

    @OneToMany(mappedBy = "matching")
    var plants: List<Plant> = arrayListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_manager_id")
    var plantManager: PlantManager = plantManager
}