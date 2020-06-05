package com.github.yuizho.springawssandbox.config

import com.amazonaws.services.s3.AmazonS3
import com.github.yuizho.springawssandbox.infra.FileOperations
import com.github.yuizho.springawssandbox.infra.S3Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.ResourceLoader


@Configuration
class FileConfig {
    @Bean
    fun fileOperations(resourceLoader: ResourceLoader): FileOperations = S3Template(resourceLoader)
}