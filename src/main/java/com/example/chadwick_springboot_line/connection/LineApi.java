package com.example.chadwick_springboot_line.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Component
public class LineApi {

    private String multicastUrl = "https://api.line.me/v2/bot/message/multicast";

    @Value("${line.authorization}")
    private String authorization;

    private String messageString;

    private String userid;

    private String body;

    public String getMessageString() {
        return messageString;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public HttpResponse<String> multicast() throws UnirestException {
        this.body = ("{\"to\": [\"" + this.userid + "\"]," +
                "\"messages\": [" + this.messageString + "]}");
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(multicastUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", authorization)
                .body(this.body)
                .asString();
        System.out.println(this.body);
        return response;

    }

}
