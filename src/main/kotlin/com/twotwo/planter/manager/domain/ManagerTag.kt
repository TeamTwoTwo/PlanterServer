package com.twotwo.planter.manager.domain

import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class ManagerTag(name: String): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name
}