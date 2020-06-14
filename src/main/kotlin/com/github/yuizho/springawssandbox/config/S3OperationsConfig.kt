package com.github.yuizho.springawssandbox.config

import com.amazonaws.services.s3.AmazonS3
import com.github.yuizho.springawssandbox.infra.S3Operations
import com.github.yuizho.springawssandbox.infra.S3Template
import org.springframework.cloud.aws.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader


@Configuration
class S3OperationsConfig {
    @Bean
    fun s3Operations(applicationContext: ApplicationContext, amazonS3: AmazonS3): S3Operations {
        return S3Template(
                PathMatchingSimpleStorageResourcePatternResolver(amazonS3, applicationContext)
        )
    }
}