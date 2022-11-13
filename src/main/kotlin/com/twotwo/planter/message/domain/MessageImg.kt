package com.twotwo.planter.message.domain

import com.twotwo.planter.util.BaseTime
import javax.persistence.*

@Entity
class MessageImg(imageUrl: String, message: Message): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_img_id")
    var id: Long? = null

    var imageUrl: String = imageUrl

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    var message: Message = message
}