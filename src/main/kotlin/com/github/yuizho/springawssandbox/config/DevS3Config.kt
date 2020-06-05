package com.github.yuizho.springawssandbox.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.findify.s3mock.S3Mock
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.ResourceLoader


@Configuration
@EnableConfigurationProperties(S3Properties::class)
class DevS3Config(
        val props: S3Properties
) {
    init {
//        val api = S3Mock.Builder()
//                .withPort(9001)
//                .withInMemoryBackend()
//                .build()
//        api.start()
    }

    @Bean
    fun amazonS3Client(): AmazonS3
            = AmazonS3ClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:9090", props.region))
            .withCredentials(AWSStaticCredentialsProvider(AnonymousAWSCredentials()))
            .withPathStyleAccessEnabled(true)
            .build()

    @Bean
    fun configureResourceLoader(amazonS3: AmazonS3, resourceLoader: DefaultResourceLoader): ResourceLoader {
        val simpleStorageProtocolResolver = SimpleStorageProtocolResolver(amazonS3)
        // As we are calling it by hand, we must initialize it properly.
        // https://stackoverflow.com/questions/47135265/cannot-be-cast-to-org-springframework-core-io-writableresource-on-spring-aws-exa
        simpleStorageProtocolResolver.afterPropertiesSet()
        resourceLoader.addProtocolResolver(simpleStorageProtocolResolver)
        return resourceLoader
    }
}

@ConfigurationProperties(prefix = "s3")
class S3Properties(
        var accessKey: String = "",
        var secretKey: String = "",
        var region: String = ""
)
