package com.ziola.shelter.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.ziola.shelter.animals.Animal;
import com.ziola.shelter.aws.BucketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class BucketsServiceImpl implements BucketsService {

    private AmazonS3 s3client;

    @Autowired
    public BucketsServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Override
    public boolean checkIfExists(String bucketName) {
        return s3client.doesBucketExistV2(bucketName);
    }

    @Override
    public void createBucket(String bucketName) {
        s3client.createBucket(bucketName);
    }

    @Override
    public List<Bucket> allAddedBuckets() {
        return s3client.listBuckets();
    }

    @Override
    public void deleteBucket(String bucketName) {
        s3client.deleteBucket(bucketName);
    }

    @Override
    public void uploadFile(String bucketName, String path, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, path, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public ObjectListing listOfAllObjects(String bucketName) {
        return s3client.listObjects(bucketName);
    }

    @Override
    public void deleteAnObject(String bucketName, Animal animal) {
        s3client.deleteObject(bucketName, animal.getCity()+ animal.getName()+animal.getAge());
    }

    @Override
    public boolean listHasObject(String bucketName, String nameOfObject) {
        ObjectListing objectListing = s3client.listObjects(bucketName);
        for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
            if (os.getKey().equals(nameOfObject)) {
                return true;
            }
        }
        return false;
    }
}
