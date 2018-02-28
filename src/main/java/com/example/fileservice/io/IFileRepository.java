package com.example.fileservice.io;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.Repository;

import java.io.File;
import java.util.List;

public interface IFileRepository {

    /**
     * Find a file based on the MongoDB bucketIdentifier
     * @param bucketIdentifier
     * @return
     */
    public File findByBucketId(String bucketIdentifier);

    /**
     *
     * @param bucketIdentifier
     * @param file
     * @return
     */
    public boolean storeFileForBucketId(String bucketIdentifier, File file);

    /**
     * Returns a list of the identifiers stored in the db
     * @return
     */
    public List<String> getBucketIds();
}
