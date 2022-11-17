package com.example.chadwick_springboot_line.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import com.example.chadwick_springboot_line.connection.LineApi;
import com.example.chadwick_springboot_line.connection.MongoDB;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.client.MongoCursor;

@Component
public class LineServiceImpl implements LineService {
    @Autowired
    private MongoDB mongoDB;

    @Autowired
    private LineApi lineApi;

    @Override
    public String receiveMessage(String messageLog) {
        MongoClient mongoClient = mongoDB.createConnection();
        try (mongoClient) {
            MongoDatabase database = mongoClient.getDatabase("line");
            MongoCollection<Document> collection = database.getCollection("messageLog");
            collection.insertOne(Document.parse(messageLog));
        } finally {
            mongoClient.close();
        }
        return "saveMessage";
    }

    @Override
    public String sendMessage(String requestBodyString) throws Exception {

        JSONObject requestBody = new JSONObject(requestBodyString);

        String message = requestBody.getString("message");

        String messageString = "{\"type\": \"text\"," +
                "\"text\": \"" + message + "\"}";

        lineApi.setUserid(requestBody.getString("userid"));
        lineApi.setMessageString(messageString);

        try {
            HttpResponse<String> response = lineApi.multicast();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "sendMessage";
    }

    @Override
    public List<String> getMessageList(String userid) throws Exception {

        MongoClient mongoClient = mongoDB.createConnection();
        List<String> list = new ArrayList();
        try (mongoClient) {
            MongoDatabase database = mongoClient.getDatabase("line");
            Bson filter = eq("events.source.userId", userid);
            Bson project = new Document("events.source.userId", true)
                    .append("events.message.text", true)
                    .append("events.timestamp", true);

            MongoCollection<Document> collection = database.getCollection("messageLog");
            MongoCursor<Document> results = collection.find(filter).projection(project).iterator();

            while (results.hasNext()) {
                String jsonResult = results.next().toJson();
                list.add(jsonResult);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            mongoClient.close();
        }

        return !list.isEmpty() ? list : null;
    }

}
