package com.github.yuizho.springawssandbox.controller.rest

import com.amazonaws.services.s3.AmazonS3
import com.github.yuizho.springawssandbox.infra.S3Operations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("s3")
class S3Controlle(
        val amazonS3: AmazonS3,
        val s3Operations: S3Operations
) {
    @GetMapping
    fun get(@RequestParam("bucket") bucket: String): String {
        if (!amazonS3.listBuckets().map { it.name }.contains(bucket)) {
            println("create ${bucket} bucket!")
            amazonS3.createBucket(bucket)
        }
        val filePath = "${bucket}/hello.txt"
        s3Operations.write(filePath, "Hello!!!")
        val actual = s3Operations.read(filePath)
        println(actual)
        return actual
    }
}