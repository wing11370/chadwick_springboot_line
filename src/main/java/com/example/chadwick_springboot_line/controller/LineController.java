package com.example.chadwick_springboot_line.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.chadwick_springboot_line.service.LineService;

@RestController
public class LineController {

    @Autowired
    private LineService lineService;

    @PostMapping("/message/receive")
    public String receiveMessage(@RequestBody String messageLog) {
        return lineService.receiveMessage(messageLog);
    }

    @PostMapping("/message/send")
    public String sendMessage(@RequestBody String requestBodyString) throws Exception {
        return lineService.sendMessage(requestBodyString);
    }

}
