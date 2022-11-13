package com.twotwo.planter.message.service

import com.twotwo.planter.message.domain.Message
import com.twotwo.planter.message.domain.SenderType
import com.twotwo.planter.message.dto.GetMessageDetailRes
import com.twotwo.planter.message.dto.GetMessageGroupRes
import com.twotwo.planter.message.repository.MessageRepository
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
@Transactional(readOnly = true)
class MessageService(private val messageRepository: MessageRepository) {
    fun getMessageList(userId: Long): Any {
        val messages = messageRepository.findAll(userId)

        val response = arrayListOf<GetMessageGroupRes>()

        for(message in messages){
            val images = arrayListOf<String>()
            if(message.images !== null){
                for(image in message.images!!){
                    images.add(image!!.imageUrl)
                }
            }
            response.add(
                GetMessageGroupRes(message.plantManager.id!!, message.plantManager.profileImg, message.plantManager.name, 0,
                    message.contents, message.createdAt!!.format(DateTimeFormatter.ofPattern("a hh:mm")), !message.isRead)
            )
        }
        return response
    }

    @Transactional
    fun createMessage(message: Message): Message {
        val insertedMessage = messageRepository.save(message)
        return insertedMessage
    }

    fun getMessageDetail(userId: Long, plantManagerId: Long): Any{
        val messages = messageRepository.findAllByUserIdAndPlantManagerIdOrderByCreatedAtDesc(userId, plantManagerId)

        val response = arrayListOf<GetMessageDetailRes>()

        for(message in messages){
            val images = arrayListOf<String>()
            if(message.images !== null){
                for(image in message.images!!){
                    images.add(image!!.imageUrl)
                }
            }
            response.add(
                GetMessageDetailRes(message.id!!, message.sender === SenderType.USER,
                    message.contents, images, message.createdAt!!.format(DateTimeFormatter.ofPattern("a hh:mm")))
            )
        }
        return response
    }

    @Transactional
    fun updateMessageStatusToDelete(userId: Long, plantManagerId: Long): Int {
        messageRepository.updateStatus(userId, plantManagerId)
        return 1
    }

    fun getMessageById(messageId: Long): Message {
        val message = messageRepository.findMessageById(messageId)
        if(message === null){
            throw BaseException(MESSAGE_NOT_FOUND)
        }
        return message
    }
}
