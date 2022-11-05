package com.twotwo.planter.message.dto

data class GetMessageGroupRes (
    val id: Long,
    val profileImg: String?,
    val name: String,
    val category: Int,
    val contents: String,
    val sentAt: String,
    val isUnread: Boolean
        )