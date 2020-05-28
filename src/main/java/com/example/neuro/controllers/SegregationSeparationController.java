package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.Test;
import com.example.neuro.beans.Vial;
import com.example.neuro.services.SegregationSeparationService;
import com.example.neuro.utils.TestCategoryEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/segregation")
public class SegregationSeparationController {

    public static Logger logger = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    SegregationSeparationService segregationSeparationService;

    @PostMapping("/separateSample")
    public String separateSample(@RequestBody Master master) throws JsonProcessingException {
        logger.info("separateSample called");
        return segregationSeparationService.separateSampleRest(master);
    }

    @GetMapping("/updateLockedCounter")
    public Test updateLockedCounter(@RequestParam String code, @RequestParam Integer lockedCounter){
        logger.info("updateLockedCounter called with code : "+code+",lockedCounter : "+lockedCounter);
        return segregationSeparationService.updateLockedCounterRest(code,lockedCounter);
    }

    @PostMapping("/updateLockedCounterWithVials")
    public Test updateLockedCounterWithVials(@RequestParam String code, @RequestParam Integer lockedCounter, @RequestBody List<Vial> vials){
        logger.info("updateLockedCounterWithVials called with code :"+code+",lockedCounter " +lockedCounter);
        return segregationSeparationService.updateLockedCounterRest(code,lockedCounter,vials);
    }

    @GetMapping("/getTestListByCodeAndDate")
    public List<Vial> getTestListByCodeAndDate(@RequestParam String testCode, @RequestParam String date) {
        logger.info("getTestListByCodeAndDate called with testCode : "+testCode+", date : "+date);
        return segregationSeparationService.getTestListByCodeAndDateRest(testCode,date);
    }

    @GetMapping("/getPatientDetailByVLID")
    public Vial getPatientDetailByVLID(@RequestParam String vlid) throws JsonProcessingException {
        logger.info("getPatientDetailByVLID called with vlid :"+vlid);
        return segregationSeparationService.getPatientDetailByVLIDRest(vlid);
    }

    @GetMapping("/getTestListByCategoryAndDate")
    public String getTestListByCategoryAndDate(@RequestParam TestCategoryEnum testCategory,@RequestParam String date) throws JsonProcessingException {
        logger.info("getTestListByCategoryAndDate called with testCategory : "+testCategory+", date : "+date);
        return segregationSeparationService.getTestListByCategoryAndDateRest(testCategory,date);
    }

    @PostMapping("/swapVialSerial")
    public List<Vial> swapVialSerial(@RequestBody String jsonString) throws JsonProcessingException {
        logger.info("swapVialSerial called");
        return segregationSeparationService.swapVialSerialRest(jsonString);
    }
}
