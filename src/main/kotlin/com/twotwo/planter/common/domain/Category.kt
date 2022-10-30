package com.twotwo.planter.common.domain

import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class Category(name: String): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name
}