package com.twotwo.planter.common.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.twotwo.planter.util.BaseException
import com.twotwo.planter.util.BaseResponseCode.*
import com.twotwo.planter.util.CommonUtil
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Slf4j
@RequiredArgsConstructor
@Service
class AwsS3Service(private val amazonS3Client: AmazonS3Client, private val commonUtil: CommonUtil) {

    val bucketName = "twotwo-planter"

    fun uploadFile(category: String, multipartFile: MultipartFile): String {
        validateFileExists(multipartFile)

        val fileName = commonUtil.buildFileName(category, multipartFile.originalFilename ?: "file")

        val objectMetadata = ObjectMetadata();
        objectMetadata.contentType = multipartFile.contentType
        println("2")

        // TODO: 예외처리
        val inputStream: InputStream = multipartFile.inputStream
        amazonS3Client.putObject(
            PutObjectRequest(bucketName, fileName, inputStream, objectMetadata).withCannedAcl(
                CannedAccessControlList.PublicRead
            )
        );
        println(amazonS3Client.getUrl(bucketName, fileName).toString())

        return amazonS3Client.getUrl(bucketName, fileName).toString()
    }

    fun validateFileExists(multipartFile: MultipartFile): Int {
        if (multipartFile.isEmpty()) {
            throw BaseException(FILE_UPLOAD_FAIL)
        }
        return 1
    }
}