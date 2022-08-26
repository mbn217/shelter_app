package com.ziola.shelter.aws;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.ziola.shelter.animals.Animal;

import java.io.File;
import java.util.List;

public interface BucketsService {

    boolean checkIfExists(String bucketName);

    void createBucket(String bucketName);

    List<Bucket> allAddedBuckets();

    void deleteBucket(String bucketName);

    void uploadFile(String bucketName, String path, File file);

    ObjectListing listOfAllObjects(String bucketName);

    void deleteAnObject(String bucketName, Animal animal);

    boolean listHasObject(String bucketName, String nameOfObject);
}
