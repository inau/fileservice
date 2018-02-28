package com.example.fileservice.db;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

    private final MongoProperties mongoprops;
    private final MongoClientURI URI;

    @Autowired
    SpringMongoConfig(MongoProperties mongoprops) {
        this.mongoprops = mongoprops;

        this.URI =
                new MongoClientURI("mongodb://"+mongoprops.getUsername()+":"+mongoprops.getPassword()+"@cluster0-shard-00-00-5msp4.mongodb.net:27017,cluster0-shard-00-01-5msp4.mongodb.net:27017,cluster0-shard-00-02-5msp4.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin");;
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Override
    protected String getDatabaseName() {
        return "file";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(this.URI);
    }

}