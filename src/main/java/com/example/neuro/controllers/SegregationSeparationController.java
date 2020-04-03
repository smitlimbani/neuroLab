package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.Test;
import com.example.neuro.beans.Vial;
import com.example.neuro.services.SegregationSeparationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/segregation")
public class SegregationSeparationController {

    @Autowired
    SegregationSeparationService segregationSeparationService;

    @PostMapping("/separateSample")
    public List<Vial> separateSample(@RequestBody Master master) throws JsonProcessingException {
        return segregationSeparationService.separateSampleRest(master);
    }

    @GetMapping("/updateLockedCounter")
    public Test updateLockedCounter(@RequestParam String code, @RequestParam Integer lockedCounter){
        return segregationSeparationService.updateLockedCounterRest(code,lockedCounter);
    }

    @PostMapping("/updateLockedCounterWithVials")
    public Test updateLockedCounterWithVials(@RequestParam String code, @RequestParam Integer lockedCounter, @RequestBody List<Vial> vials){
        return segregationSeparationService.updateLockedCounterRest(code,lockedCounter,vials);
    }

    @PostMapping("/getCurrentTestList")
    public List<Vial> getCurrentTestList(@RequestBody @Valid String jsonString) throws JsonProcessingException {
        return segregationSeparationService.getCurrentTestListRest(jsonString);
    }

    @PostMapping("/getPatientDetailByVLID")
    public String getPatientDetailByVLID(@RequestBody @Valid String jsonString) throws JsonProcessingException {
        return segregationSeparationService.getPatientDetailByVLIDRest(jsonString);
    }
}
