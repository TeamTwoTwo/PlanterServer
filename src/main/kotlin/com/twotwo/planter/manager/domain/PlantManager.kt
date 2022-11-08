package com.twotwo.planter.manager.domain

import com.twotwo.planter.review.domain.Review
import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class PlantManager(name: String, profileImg: String, description: String, caringPrice: Int, pruningPrice: Int, address: String, latitude: Double, longitude: Double, isPhoto: Boolean, category: PlantManagerCategory, introduction: String): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_manager_id")
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name

    var profileImg: String = profileImg
    var description: String = description
    var caringPrice: Int = caringPrice
    var pruningPrice: Int = pruningPrice
    var address: String = address
    var latitude: Double = latitude
    var longitude: Double = longitude
    var isPhoto: Boolean = isPhoto
    var introduction: String = introduction

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var category: PlantManagerCategory = category

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "plantManager")
    var images: List<ManagerImg?>? = ArrayList<ManagerImg>()

    @OneToMany(mappedBy = "plantManager")
    var plantCares: List<PlantCareOption>? = ArrayList<PlantCareOption>()
}