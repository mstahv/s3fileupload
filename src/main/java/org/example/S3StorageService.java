package org.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class S3StorageService {

    private final AmazonS3 s3Client;
    // You probably want to configure this in a properties file as well
    // or beg me to give access to my bucket 😁
    private final String bucketName = "mstahv-vaadin-upload-example";

    // The AWS credentials (give e.g. as environment variables)
    public S3StorageService(@Value("${awsaccess}") String awsKey, @Value("${awssecret}") String awsSecret) {
        AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                // I happen to use this DS, you might want to change it
                .withRegion(Regions.EU_NORTH_1)
                .build();
    }


    public void uploadFile(String filename, InputStream content) {
        s3Client.putObject(bucketName, filename, content, null);
    }

    public List<MyFile> listFiles() {
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries().stream()
                .map(s -> new MyFile(s.getKey(), s.getSize(), s.getLastModified()))
                .toList();
    }

    public InputStream getFile(String filename) {
        return s3Client.getObject(bucketName, filename).getObjectContent();
    }

}
