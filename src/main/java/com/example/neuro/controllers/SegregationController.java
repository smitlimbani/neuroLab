package com.example.neuro.controllers;

import com.example.neuro.services.JsonService;
import com.example.neuro.services.SegregationService;
import com.example.neuro.services.VialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping("segregation/")
public class SegregationController {

    @Autowired
    SegregationService segregationService;

    @PostMapping("/getCurrentTestList")
    public String getCurrentTestList(@RequestBody @Valid String jsonString) throws JsonProcessingException {
        return segregationService.getCurrentTestListRest(jsonString);
    }

    @PostMapping("/getPatientDetailByVLID")
    public String getPatientDetailByVLID(@RequestBody @Valid String jsonString) throws JsonProcessingException {
        return segregationService.getPatientDetailByVLIDRest(jsonString);
    }
}
