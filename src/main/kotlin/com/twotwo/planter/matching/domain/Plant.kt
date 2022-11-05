package com.twotwo.planter.matching.domain

import com.twotwo.planter.util.BaseTime
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import javax.persistence.*

@Entity
class Plant(name: String, care: Boolean, pruning: Boolean, matching: Matching): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    var id: Long? = null

    @Column(nullable = false)
    var name: String = name

    @Column(nullable = false)
    var care: Boolean = care

    @Column(nullable = false)
    var pruning: Boolean = pruning

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    var matching: Matching = matching
}