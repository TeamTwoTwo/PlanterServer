package com.twotwo.planter.manager.domain

import com.twotwo.planter.common.domain.Category
import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class PlantManager(name: String, profileImg: String, description: String, caringPrice: Int, pruningPrice: Int, address: String, latitude: Double, longitude: Double): BaseTime() {
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

    var address: String = description
    var latitude: Double = latitude
    var longitude: Double = longitude

    @OneToOne
    var category: Category? = null

    @OneToMany(mappedBy = "plant-manager")
    var images: List<ManagerImg> = ArrayList<ManagerImg>()
}