// camel-k: language=java
package test;

import org.apache.camel.BindToRegistry;
import org.apache.camel.PropertyInject;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

public class MinioCustomizer {

    @BindToRegistry
    public static S3Client minioClient(
            @PropertyInject("minio.endpoint") String endpointAddress,
            @PropertyInject("minio.access-key") String accessKey,
            @PropertyInject("minio.secret-key") String secretKey) {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
            accessKey,
            secretKey);

            // should work : https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-s3.html
            // or not since it is 2.18, so nouw let's try the 2.17 way
        Region region = Region.US_EAST_1;
        return S3Client.builder()
            .region(region)
            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
            .endpointOverride(URI.create(endpointAddress))
            //.serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .build();

    }

}
