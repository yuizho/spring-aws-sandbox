package com.github.yuizho.springawssandbox.infra

import com.amazonaws.services.s3.AmazonS3
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import java.io.BufferedReader

interface FileOperations {
    fun write(path: String, fileBody: String)
    fun read(path: String): String
}

class S3Template(
        private val resourceLoader: ResourceLoader
) : FileOperations {

    override fun write(path: String, fileBody: String) {
        val resource = resourceLoader.getResource("s3://${path}/test.txt") as WritableResource
        resource.outputStream.bufferedWriter().use {
            it.write(fileBody)
        }
    }

    override fun read(path: String): String {
        val resource = resourceLoader.getResource("s3://${path}/test.txt")
        return resource.inputStream.bufferedReader().use(BufferedReader::readText)
    }
}