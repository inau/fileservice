package com.example.fileservice.db;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {

    private final String uri;

    @Autowired
    public SpringMongoConfig(@Value("${spring.data.mongodb.uri}") String uri) {
        this.uri = uri;
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Override
    protected String getDatabaseName() {
        return "files";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient( new MongoClientURI(uri) );
    }

}