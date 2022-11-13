package com.twotwo.planter.auth.dto

data class SendSmsReq (val type: String, val contentType: String, val countryCode: String, val from: String, val subject: String, val content: String, val messages: List<Message>)