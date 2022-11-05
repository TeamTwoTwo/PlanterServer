package com.twotwo.planter.message.service

import com.twotwo.planter.message.domain.Message
import com.twotwo.planter.message.repository.MessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MessageService(private val messageRepository: MessageRepository) {
    fun getMessageList(userId: Long): List<Message> {
        return messageRepository.findAll(userId)
    }

    @Transactional
    fun createMessage(message: Message): Message {
        val insertedMessage = messageRepository.save(message)
        return insertedMessage
    }

    fun getMessageDetail(userId: Long, plantManagerId: Long): List<Message> {
        return messageRepository.findAllByUserIdAndPlantManagerIdOrderByCreatedAtDesc(userId, plantManagerId)
    }

    @Transactional
    fun updateMessageStatusToDelete(userId: Long, plantManagerId: Long): Int {
        messageRepository.updateStatus(userId, plantManagerId)
        return 1
    }
}