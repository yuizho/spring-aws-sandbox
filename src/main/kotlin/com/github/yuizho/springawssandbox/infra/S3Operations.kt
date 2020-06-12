package com.github.yuizho.springawssandbox.infra

import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.WritableResource
import org.springframework.core.io.support.ResourcePatternResolver
import java.io.BufferedReader

interface S3Operations {
    fun write(filePath: String, fileBody: String)
    fun read(filePath: String): String
    fun readResources(filePattern: String): Array<Resource>
}

class S3Template(
        private val resourcePatternResolver: ResourcePatternResolver,
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

    override fun readResources(filePattern: String): Array<Resource> {
        val resources = resourcePatternResolver.getResources("s3://$filePattern")
        return resources
    }


}