package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.services.JsonService;
import com.example.neuro.services.MasterService;
import com.example.neuro.services.ReceivingStationService;
import com.example.neuro.services.VariableService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/rec")
public class ReceivingStationController {

    @Autowired
    ReceivingStationService receivingStationService;
    @Autowired
    VariableService variableService;
    @Autowired
    MasterService masterService;

    @GetMapping("/getUnprocessedSampleList")
    public String getUnprocessedSampleList() throws JsonProcessingException {
        return receivingStationService.getUnprocessedSampleListRest();
    }

    @PostMapping("/confirmSampleNotReceived")
    public String confirmSampleNotReceived(@RequestBody String jsonString)throws JsonProcessingException{
        return receivingStationService.confirmSampleNotReceivedRest(jsonString);
    }

    @PostMapping("/linkSamples")
    public String linkSamples(@RequestBody String jsonString)throws JsonProcessingException{
        return receivingStationService.linkSamplesRest(jsonString);
    }

    @PostMapping("/test")
    public String jsonServiceTestFunc(@RequestBody @Valid String jsonString) throws JsonProcessingException {
        List<Master> masters = (new JsonService<Master>()).fromJsonList(jsonString, "masters", Master.class);
        System.out.println(masters.get(0));
        Master master= masters.get(0);
        System.out.println(master.getStatus());
        return "ok";
    }

    @GetMapping("/getList")
    public String getJsonList() throws JsonProcessingException {
        List<Master> list = masterService.getMastersRest();
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringy = mapper.writerWithDefaultPrettyPrinter().withRootName("masters").writeValueAsString(list);
        return jsonStringy;
    }
}
