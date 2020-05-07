package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.Vial;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.VariableRepository;
import com.example.neuro.services.*;
import com.example.neuro.utils.TestCategoryEnum;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
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
    @Autowired
    MasterRepository masterRepository;
    @Autowired
    VariableRepository variableRepository;
    @Autowired
    SegregationSeparationService segregationSeparationService;

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
    public List<Master> test(@RequestParam Date startDate, @RequestParam Date endDate) {
//        segregationSeparationService.getCurrentTestListByCategoryRest(TestCategoryEnum.BLOT,"2020-04-23");
        return receiveStationPService.test(startDate,endDate);
//        return true;
//        return jsonService.toJson(master,"master");
    }
}
