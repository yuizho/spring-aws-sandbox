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
            amazonS3.createBucket(bucket)
        }
        s3Operations.write(bucket, "Hello!!!")
        val actual = s3Operations.read(bucket)
        println(actual)
        return actual
    }
}