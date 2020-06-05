package com.github.yuizho.springawssandbox

import com.github.yuizho.springawssandbox.config.DevS3Config
import com.github.yuizho.springawssandbox.config.FileConfig
import com.github.yuizho.springawssandbox.infra.FileOperations
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [DevS3Config::class, FileConfig::class])
class SpringAwsSandboxApplicationTests {

    @Autowired
    lateinit var s3Template: FileOperations

    @Test
    fun contextLoads() {
        s3Template.write("test", "Hello!!!")
        val actual = s3Template.read("test")
        println(actual)
    }

}
