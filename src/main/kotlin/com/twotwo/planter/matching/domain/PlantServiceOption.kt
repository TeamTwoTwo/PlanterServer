package com.twotwo.planter.matching.domain

import com.twotwo.planter.manager.domain.PlantCareOption
import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class PlantServiceOption(plantService: PlantService, plantCareOption: PlantCareOption): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_service_option_id")
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "plant_service_id")
    var plantService: PlantService = plantService

    @ManyToOne
    @JoinColumn(name = "plant_care_option_id")
    var plantCareOption: PlantCareOption = plantCareOption
}