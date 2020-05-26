package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.beans.Sample;
import com.example.neuro.beans.Vial;
import com.example.neuro.services.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard/")
public class DashboardController {

    public static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/getCompleteDetailByUHID")
    public PatientDemographicDetail getCompleteDetailByUHID(@RequestParam String uhid){
        System.out.println(uhid);
        logger.info("!!..Logger working..!! .. 1 - getCompleteDetailByUHID");
        return dashboardService.getCompleteDetailByUHIDRest(uhid);
    }

    @GetMapping("/getCompleteDetailByULID")
    public Master getCompleteDetailByULID(@RequestParam String ulid){
        System.out.println(ulid);

        logger.info("!!..Logger working..!! .. 2 - getCompleteDetailByULID");
        return dashboardService.getCompleteDetailByULIDRest(ulid);
    }

    @GetMapping("/getCompleteDetailBySampleId")
    public Sample getCompleteDetailBySampleId(@RequestParam String sampleId){
        System.out.println(sampleId);
        return dashboardService.getCompleteDetailBySampleIdRest(sampleId);
    }

    @GetMapping("/getCompleteDetailByVLID")
    public Vial getCompleteDetailByVLID(@RequestParam String vlid){
        System.out.println(vlid);
        return dashboardService.getCompleteDetailByVLIDRest(vlid);
    }
}
