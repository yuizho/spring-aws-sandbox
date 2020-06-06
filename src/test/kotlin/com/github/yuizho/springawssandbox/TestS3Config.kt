package com.github.yuizho.springawssandbox

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import io.findify.s3mock.S3Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.aws.core.io.s3.SimpleStorageProtocolResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.DefaultResourceLoader


@Configuration
class TestS3Config {
    /**
     * start S3 mock for testing
     */
    init {
        val api = S3Mock.Builder().withPort(9090).withInMemoryBackend().build()
        api.start()
    }

    @Bean
    @Primary
    fun amazonS3Client(): AmazonS3
            = AmazonS3ClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:9090", "us-east-1"))
            .withCredentials(AWSStaticCredentialsProvider(AnonymousAWSCredentials()))
            .withPathStyleAccessEnabled(true)
            .build()

    /**
     * When I apply @EnableContextLoader to configure the ResourceLoader for S3,
     * the ResourceLoader will access real S3 on AWS.
     * But this config class is just for testing.
     * - https://stackoverflow.com/questions/53092222/spring-cloud-testing-s3-client-with-testcontainters
     *
     * Thats why this method sets S3 ProtocolResolver to DefaultResourceLoader by hand.
     * https://stackoverflow.com/questions/47135265/cannot-be-cast-to-org-springframework-core-io-writableresource-on-spring-aws-exa
     */
    @Autowired
    fun configureResourceLoader(amazonS3: AmazonS3, resourceLoader: DefaultResourceLoader) {
        val simpleStorageProtocolResolver = SimpleStorageProtocolResolver(amazonS3)
        // As we are calling it by hand, we must initialize it properly.
        simpleStorageProtocolResolver.afterPropertiesSet()
        resourceLoader.addProtocolResolver(simpleStorageProtocolResolver)
    }
}
