package com.example.chadwick_springboot_line.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

}
