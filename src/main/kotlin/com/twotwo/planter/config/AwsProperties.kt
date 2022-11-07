package com.twotwo.planter.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.aws.credentials")
data class AwsProperties (
    val accessKey: String,
    val secretKey: String
)