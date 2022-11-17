package com.example.chadwick_springboot_line.service;

import java.util.List;

import org.json.JSONException;

public interface LineService {

    String receiveMessage(String messageLog);

    String sendMessage(String body) throws Exception;

}
