package com.github.yuizho.springawssandbox.infra

import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import java.io.BufferedReader

interface S3Operations {
    fun write(path: String, fileBody: String)
    fun read(path: String): String
}

class S3Template(
        private val resourceLoader: ResourceLoader
) : S3Operations {

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