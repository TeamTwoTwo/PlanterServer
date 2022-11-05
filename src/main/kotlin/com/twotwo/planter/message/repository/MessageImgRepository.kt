package com.twotwo.planter.message.repository

import com.twotwo.planter.message.domain.MessageImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageImgRepository: JpaRepository<MessageImg, Long> {
}