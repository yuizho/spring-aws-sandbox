package com.github.yuizho.springawssandbox.infra

import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import java.io.BufferedReader

interface S3Operations {
    fun write(filePath: String, fileBody: String)
    fun read(filePath: String): String
}

class S3Template(
        private val resourceLoader: ResourceLoader
) : S3Operations {

    override fun write(filePath: String, fileBody: String) {
        val resource = resourceLoader.getResource("s3://${filePath}") as WritableResource
        resource.outputStream.bufferedWriter().use {
            it.write(fileBody)
        }
    }

    override fun read(filePath: String): String {
        val resource = resourceLoader.getResource("s3://${filePath}")
        return resource.inputStream.bufferedReader().use(BufferedReader::readText)
    }
}