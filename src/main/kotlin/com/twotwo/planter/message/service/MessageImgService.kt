package com.twotwo.planter.message.service

import com.twotwo.planter.message.domain.MessageImg
import com.twotwo.planter.message.repository.MessageImgRepository
import org.springframework.stereotype.Service

@Service
class MessageImgService(private val messageImgRepository: MessageImgRepository) {
    fun createMessageImg(messageImg: MessageImg): Long {
        messageImgRepository.save(messageImg)
        return messageImg.id!!
    }
}
