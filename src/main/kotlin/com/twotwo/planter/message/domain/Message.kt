package com.twotwo.planter.message.domain

import com.twotwo.planter.manager.domain.PlantManager
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.util.BaseTime
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import javax.persistence.*

@Entity
@DynamicInsert
class Message(contents: String, status: MessageStatus, isRead: Boolean, sender: SenderType, user: User, plantManager: PlantManager): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    var id: Long? = null

    var contents: String = contents

    @Column(nullable = false)
    @ColumnDefault("ACTIVE")
    @Enumerated(EnumType.STRING)
    var status: MessageStatus = status

    @Column(nullable = false)
    @ColumnDefault("false")
    var isRead: Boolean = isRead

    @Column(nullable = false)
    @ColumnDefault("USER")
    @Enumerated(EnumType.STRING)
    var sender: SenderType = sender

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_manager_id")
    var plantManager: PlantManager = plantManager

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "message")
    var images: List<MessageImg?>? = ArrayList<MessageImg>()
}