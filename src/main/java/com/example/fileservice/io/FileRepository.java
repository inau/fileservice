package com.example.fileservice.io;

import com.example.fileservice.db.SpringMongoConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.List;

public class FileRepository implements IFileRepository {

    @Override
    public File findByBucketId(String bucketIdentifier) {
        return null;
    }

    @Override
    public boolean storeFileForBucketId(String bucketIdentifier, File file) {

        if ( !file.exists() ) {
            return false;
        }

        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        GridFsOperations gridOperations =
                (GridFsOperations) ctx.getBean("gridFsTemplate");

        DBObject metaData = new BasicDBObject();
        metaData.put("extra1", "anything 1");
        metaData.put("extra2", "anything 2");

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            gridOperations.store(inputStream, "testing.png", "image/png", metaData);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");


        return true;
    }

    @Override
    public List<String> getBucketIds() {
        return null;
    }
}
