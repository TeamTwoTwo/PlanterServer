package com.twotwo.planter.matching.domain

import com.twotwo.planter.manager.domain.PlantCareOption
import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class PlantService(name: String, count: Int, matching: Matching, plantCareOption: PlantCareOption): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name

    @Column(nullable = false)
    var count: Int = count

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    var matching: Matching = matching

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plant_care_option_id")
    var plantCareOption: PlantCareOption = plantCareOption
}