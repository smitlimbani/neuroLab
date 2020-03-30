package com.example.neuro.controllers;

import com.example.neuro.service.ReceiveStationPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/")
public class recieveStationPController {
    @Autowired
    ReceiveStationPService receiveStationPService;

    @GetMapping("/getNextXULID")
    public String getNextXULID(){
        return receiveStationPService.getNextXULIDRest();
    }

    @GetMapping("/getNextIULID")
    public String getNextIULID() { return receiveStationPService.getNextIULIDRest(); }
}
