package com.github.yuizho.springawssandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringAwsSandboxApplication

fun main(args: Array<String>) {
    runApplication<SpringAwsSandboxApplication>(*args)
}
