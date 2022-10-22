package com.twotwo.planter.auth.service

import lombok.RequiredArgsConstructor
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@RequiredArgsConstructor
@Repository
class CertificateCodeService(private val redisTemplate: RedisTemplate<String, String>) {
    val PREFIX = "signup:"
    val LIMIT_TIME: Long = (3 * 60).toLong() // 유효시간 3분

    fun createSmsCertification(phone: String, certificationNumber: String?) {
        redisTemplate.opsForValue()
            .set(PREFIX + phone, certificationNumber!!, Duration.ofSeconds(LIMIT_TIME))
    }

    fun getSmsCertification(phone: String): String? {
        return redisTemplate.opsForValue().get(PREFIX + phone)
    }

    fun removeSmsCertification(phone: String) {
        redisTemplate.delete(PREFIX + phone)
    }

    fun hasKey(phone: String): Boolean {
        return redisTemplate.hasKey(PREFIX + phone)
    }
}