package com.twotwo.planter.manager.domain

import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class ManagerImg(image: String, plantManager: PlantManager): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_img_id")
    var id: Long? = null

    var image: String = image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    var plantManager: PlantManager = plantManager
}