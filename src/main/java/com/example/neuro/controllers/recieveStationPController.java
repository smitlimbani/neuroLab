package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.Vial;
import com.example.neuro.services.JsonService;
import com.example.neuro.services.MasterService;
import com.example.neuro.services.ReceiveStationPService;
import com.example.neuro.services.VialService;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("test/")
public class recieveStationPController {
    //spelling hoti hai recEIve.
    @Autowired
    ReceiveStationPService receiveStationPService;
    @Autowired
    MasterService masterService;
    @Autowired
    VialService vialService;
    @Autowired
    JsonService jsonService;

    @GetMapping("/getNextXULID")
    public String getNextXULID(@RequestParam String sampleType){
        return receiveStationPService.getNextXULIDRest(sampleType);
    }

    @GetMapping("/getNextIULID")
    public String getNextIULID(@RequestParam String sampleType) { return receiveStationPService.getNextIULIDRest(sampleType); }

    @GetMapping("/mapExternal")
    public String mapExternal(@RequestParam String ulid,@RequestParam String uhid,@RequestParam String sampleId){
        return receiveStationPService.mapExternalRest(ulid,uhid,sampleId);
    }

    @PostMapping("/storeXPatientDetail")
    public String storeXPatientDetail(@RequestBody String jsonString) throws JsonProcessingException {
        return receiveStationPService.storeXPatientDetailRest(jsonString);
    }

    @PostMapping("/separateSample")
    public List<Vial> separateSample(@RequestBody Master master) throws JsonProcessingException {
        return receiveStationPService.separateSampleRest(master);
    }

    @GetMapping("/test")
    public String test() throws JsonProcessingException {
        Master master = masterService.getMasterRest(1);
        System.out.println(master);
        return jsonService.toJson(master,"master");

    }
}
