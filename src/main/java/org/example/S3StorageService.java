package org.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;

public class S3StorageService {

    private final AmazonS3 s3Client;

    public S3StorageService(@Value("${awsaccess}") String awsKey, @Value("${awssecret}") String awsSecret) {
        AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_NORTH_1)
                .build();
    }


}
