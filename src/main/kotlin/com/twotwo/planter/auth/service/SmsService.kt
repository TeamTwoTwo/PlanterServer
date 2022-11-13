package com.twotwo.planter.auth.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.twotwo.planter.auth.dto.Message
import com.twotwo.planter.auth.dto.SendSmsReq
import com.twotwo.planter.auth.dto.SendSmsRes
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.io.UnsupportedEncodingException
import java.lang.StringBuilder
import java.net.URI
import java.net.URISyntaxException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

@Component
class SmsService {
    val serviceId="ncp:sms:kr:264951752007:planter"
    val secretKey="qSEaZHgUavl9BlMRrUer6lZSckBnWQH0laeDLxf5"
    val accessKeyId="v76s3DmOjvqvM69MsP4i"
    val from="01047265602"

    @Throws(
        ParseException::class,
        JsonProcessingException::class,
        UnsupportedEncodingException::class,
        InvalidKeyException::class,
        NoSuchAlgorithmException::class,
        URISyntaxException::class
    )

    fun sendSms(recipientPhoneNumber: String, content: String): SendSmsRes? {
        val time: Long = System.currentTimeMillis()
        val messages: MutableList<Message> = ArrayList()
        messages.add(Message(recipientPhoneNumber, content))

        val smsRequestDto = SendSmsReq("SMS", "COMM", "82", from, "MangoLtd", "contents", messages)

        val objectMapper = ObjectMapper()
        val jsonBody: String = objectMapper.writeValueAsString(smsRequestDto)

        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.set("x-ncp-apigw-timestamp", time.toString())
        headers.set("x-ncp-iam-access-key", accessKeyId)

        val sig = makeSignature(time)
        println("sig -> $sig")
        headers.set("x-ncp-apigw-signature-v2", sig)

        val body: HttpEntity<String> = HttpEntity(jsonBody, headers)

        val restTemplate = RestTemplate()
        val sendSmsRes = restTemplate.postForObject(
            URI(
                "https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"
            ), body, SendSmsRes::class.java)
        return sendSmsRes
    }

    @Throws(UnsupportedEncodingException::class, InvalidKeyException::class, NoSuchAlgorithmException::class)
    fun makeSignature(time: Long): String {
        val space = " " // one space
        val newLine = "\n" // new line
        val method = "POST" // method
        val url = "/sms/v2/services/" + serviceId
            .toString() + "/messages"
        val timestamp = time.toString()
        val accessKey: String = accessKeyId
        val secretKey: String = secretKey
        val message = StringBuilder()
            .append(method)
            .append(space)
            .append(url)
            .append(newLine)
            .append(timestamp)
            .append(newLine)
            .append(accessKey)
            .toString()
        val signingKey = SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), "HmacSHA256")
        val mac: Mac = Mac.getInstance("HmacSHA256")
        mac.init(signingKey)
        val rawHmac: ByteArray = mac.doFinal(message.toByteArray(charset("UTF-8")))
        return Base64.getEncoder().encodeToString(rawHmac)
    }
}

