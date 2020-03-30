package com.example.neuro.controllers;

import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.services.ReceivingStationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rec")
public class ReceivingStationController {

    @Autowired
    ReceivingStationService receivingStationService;

    @GetMapping("/getUnprocessedSampleList")
    public String getUnprocessedSampleList() throws JsonProcessingException {
        return receivingStationService.getUnprocessedSampleList();
    }
}
