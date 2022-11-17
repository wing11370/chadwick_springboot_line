package com.example.chadwick_springboot_line.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Component
public class MongoDB {
    @Value("${mongo.uri}")
    private String uri;

    public MongoClient createConnection() {
        MongoClient mongoClient = MongoClients.create(uri);
        return mongoClient;
    }
}
