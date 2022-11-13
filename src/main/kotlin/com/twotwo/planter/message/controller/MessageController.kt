package com.twotwo.planter.message.controller

import com.twotwo.planter.common.service.AwsS3Service
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
class MessageController(private val messageService: MessageService, private val messageImgService: MessageImgService, private val userService: UserService, private val plantManagerService: PlantManagerService, private val awsS3Service: AwsS3Service) {
    @GetMapping("/messages")
    fun getMessageList(authentication: Authentication): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val response = messageService.getMessageList(user.id!!)

        return BaseResponse(response)
    }

    @GetMapping("/plant-managers/{plantManagerId}/messages")
    fun getMessage(authentication: Authentication, @PathVariable plantManagerId: Long): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val response = messageService.getMessageDetail(user.id!!, plantManagerId)

        return BaseResponse(response)
    }

    @PostMapping("/messages")
    fun sendMessage(authentication: Authentication, @ModelAttribute sendMessageReq: SendMessageReq): BaseResponse<Any> {
        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)
        val plantManager = plantManagerService.getPlantManager(sendMessageReq.plantManagerId)

        val message = messageService.createMessage(Message(sendMessageReq.contents, MessageStatus.ACTIVE, false, SenderType.USER, user, plantManager))
        if(sendMessageReq.images !== null){
            for(image in sendMessageReq.images){
                val uploadedUrl = awsS3Service.uploadFile("message", image)
                messageImgService.createMessageImg(MessageImg(uploadedUrl, message))
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