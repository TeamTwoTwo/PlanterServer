package com.twotwo.planter.manager.domain

import javax.persistence.*

@Entity
class ManagerImg(image: String, plantManager: PlantManager) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_img_id")
    var id: Long? = null

    var image: String = image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    var plantManager: PlantManager = plantManager
}