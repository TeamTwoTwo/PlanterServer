package com.twotwo.planter.message.service

import com.twotwo.planter.message.domain.MessageImg
import com.twotwo.planter.message.repository.MessageImgRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MessageImgService(private val messageImgRepository: MessageImgRepository) {
    @Transactional
    fun createMessageImg(messageImg: MessageImg): Long {
        messageImgRepository.save(messageImg)
        return messageImg.id!!
    }
}
