package com.ziola.shelter.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.regions.Regions.EU_CENTRAL_1;

@Configuration
public class AwsConfig {

    private AWSCredentials credentials = new BasicAWSCredentials(
            "AKIAW35C6MA3TCV2STDJ",
            "A3CmU0PubY2sXoJRdylteWEQVMuJTPmbrOWZ+6P8"
    );

    private AmazonS3 s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(EU_CENTRAL_1)
            .build();

    @Bean
    public AmazonS3 client() {
        return s3client;
    }
}
