package com.twotwo.planter.message.controller

import com.twotwo.planter.manager.service.PlantManagerService
import com.twotwo.planter.message.domain.Message
import com.twotwo.planter.message.domain.MessageImg
import com.twotwo.planter.message.domain.MessageStatus
import com.twotwo.planter.message.domain.SenderType
import com.twotwo.planter.message.dto.GetMessageDetailRes
import com.twotwo.planter.message.dto.GetMessageGroupRes
import com.twotwo.planter.message.dto.SendMessageReq
import com.twotwo.planter.message.dto.SendMessageRes
import com.twotwo.planter.message.service.MessageImgService
import com.twotwo.planter.message.service.MessageService
import com.twotwo.planter.user.service.UserService
import com.twotwo.planter.util.BaseResponse
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("")
class MessageController(private val messageService: MessageService, private val messageImgService: MessageImgService, private val userService: UserService, private val plantManagerService: PlantManagerService) {
    @GetMapping("/messages")
    fun getMessageList(authentication: Authentication): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val messages = messageService.getMessageList(user.id!!)
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

        return BaseResponse(response)
    }

    @GetMapping("/plant-managers/{plantManagerId}/messages")
    fun getMessage(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val messages = messageService.getMessageDetail(user.id!!, plantManagerId)
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

        return BaseResponse(response)
    }

    @PostMapping("/messages")
    fun sendMessage(authentication: Authentication, @RequestBody sendMessageReq: SendMessageReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)
        val plantManager = plantManagerService.getPlantManager(sendMessageReq.plantManagerId)

        val message = messageService.createMessage(Message(sendMessageReq.contents, MessageStatus.ACTIVE, false, SenderType.USER, user, plantManager))
        if(sendMessageReq.images !== null){
            for(image in sendMessageReq.images){
                messageImgService.createMessageImg(MessageImg(image, message))
            }
        }
        val response = SendMessageRes(message.id!!)

        return BaseResponse(response)
    }

    @PatchMapping("/plant-managers/{plantManagerId}/messages/status")
    fun deleteMessage(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        messageService.updateMessageStatusToDelete(user.id!!, plantManagerId)

        return BaseResponse(BaseResponseCode.SUCCESS)
    }
}