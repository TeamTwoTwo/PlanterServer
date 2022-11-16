package com.twotwo.planter.user.service

import com.twotwo.planter.common.service.AwsS3Service
import com.twotwo.planter.security.JwtTokenProvider
import com.twotwo.planter.user.domain.User
import com.twotwo.planter.user.domain.UserStatus
import com.twotwo.planter.user.repository.UserRepository
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository, private val awsS3Service: AwsS3Service) {
    @Transactional
    fun deleteUser(userId: Long) {
        userRepository.deleteById(userId)
    }
    fun findUser(email: String): User {
        return userRepository.findByEmail(email)
    }

    @Transactional
    fun updateUserStatus(userId: Long, status: UserStatus): User {
        val user = userRepository.findUserById(userId)

        if(user.status === UserStatus.DELETED){
            throw BaseException(BaseResponseCode.USER_ALREADY_DELETED)
        }

        user.status = UserStatus.DELETED
        userRepository.save(user)

        return user
    }

    @Transactional
    fun updateUserLocation(userId: Long, address: String, detailAddress: String, simpleAddress: String): User {
        val user = userRepository.findUserById(userId)

        user.address = address
        user.detailAddress = detailAddress
        user.simpleAddress = simpleAddress
        userRepository.save(user)

        return user
    }

    @Transactional
    fun updateUserInfo(userId: Long, profileImg: MultipartFile?, profileImgUrl: String?, nickname: String?): User {
        val user = userRepository.findUserById(userId)

        if(profileImgUrl === null){
            user.profileImg = null
        }

        if(profileImg != null){
            val uploadedUrl = awsS3Service.uploadFile("profile", profileImg)
            user.profileImg = uploadedUrl
        }

        if(nickname != null){
            user.nickname = nickname
        }

        userRepository.save(user)

        return user
    }
}
