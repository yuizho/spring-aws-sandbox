package com.github.yuizho.springawssandbox.config

import com.github.yuizho.springawssandbox.infra.S3Operations
import com.github.yuizho.springawssandbox.infra.S3Template
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader


@Configuration
class S3OperationsConfig {
    @Bean
    fun s3Operations(resourceLoader: ResourceLoader): S3Operations = S3Template(resourceLoader)
}