package com.twotwo.planter.manager.domain

import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class PlantCareOption(name: String, price: Int, plantManager: PlantManager): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_care_option_id")
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name
    var price: Int = price

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_manager_id")
    var plantManager: PlantManager = plantManager
}