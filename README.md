# spring-aws-sandbox
a sandbox project of spring-cloud-aws

# getting started
```
$ ./mvnw spring-boot:run
```

This App uses DefaultAwsCredentialsChain to get aws credentials.
Therefore this App looks for credentials in this order.

- environment variable
- Java System Properties
- Credential profiles file at `~/.aws/credentials`
- etc...

For more details please read this document.
https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/auth/DefaultAWSCredentialsProviderChain.html

# sample endpoints
## S3
### write/read resource by resourceLoader
Get: `http://localhost:8080/s3?bucket=<your bucket name>`

- `hello.txt` file is pushed in the bucket
- reference
  - https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html#_downloading_files
  - https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html#_uploading_files

### read resouces by PathMatchingSimpleStorageResourcePatternResolver
Get: `http://localhost:8080/s3/resources?bucket=<your bucket name>&pattern=<file pattern>`

- you can use Ant-style pattern to fetch multiple files in the bucket
  - e.g. `*.txt`
- reference
  - https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html#_searching_resources
