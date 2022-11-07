package com.twotwo.planter.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AwsConfig(private val awsProperties: AwsProperties) {

    private val accessKey = awsProperties.accessKey
    private val secretKey = awsProperties.secretKey
    private val region = "ap-northeast-2"

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val basicAWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
            .build() as AmazonS3Client
    }
}