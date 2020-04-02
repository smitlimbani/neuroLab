package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.Vial;
import com.example.neuro.services.MasterService;
import com.example.neuro.services.ReceiveStationPService;
import com.example.neuro.services.VialService;
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


    @GetMapping("/getNextXULID")
    public String getNextXULID(@RequestParam String sampleType){
        return receiveStationPService.getNextXULIDRest(sampleType);
    }

    @GetMapping("/getNextIULID")
    public String getNextIULID(@RequestParam String sampleType) { return receiveStationPService.getNextIULIDRest(sampleType); }

    @PostMapping("/storeXPatientDetail")
    public String storeXPatientDetail(@RequestBody String jsonString) throws JsonProcessingException {
        return receiveStationPService.storeXPatientDetailRest(jsonString);
    }

    @PostMapping("/seperateSample")
    public List<Vial> seperateSample(@RequestBody Master master) throws JsonProcessingException {
        return receiveStationPService.seperateSampleRest(master);
    }

    @GetMapping("/test")
    public List<Vial> test() throws JsonProcessingException {
        List<Integer> ids = new LinkedList<>();
        ids.add(2);
        ids.add(3);
        return vialService.getVialsByIdsRest(ids);
    }
}
