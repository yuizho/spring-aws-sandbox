package com.github.yuizho.springawssandbox

import com.amazonaws.services.s3.AmazonS3
import com.github.yuizho.springawssandbox.config.S3OperationsConfig
import com.github.yuizho.springawssandbox.infra.S3Operations
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TestS3Config::class, S3OperationsConfig::class])
class SpringAwsSandboxApplicationTests {

    @Autowired
    lateinit var s3Template: S3Operations

    @Autowired
    lateinit var amazonS3: AmazonS3

    @Test
    fun contextLoads() {
        amazonS3.createBucket("test")
        s3Template.write("test/test.txt", "Hello!!!")
        val actual = s3Template.read("test/test.txt")
        assertThat(actual).isEqualTo("Hello!!!")
    }

}
