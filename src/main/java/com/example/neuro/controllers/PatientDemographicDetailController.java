package com.example.neuro.controllers;

import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.services.PatientDemographicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("patientDemographicDetail/")
public class PatientDemographicDetailController {

    @Autowired
    private PatientDemographicDetailService patientDemographicDetailService;

    @GetMapping("/getAll")
    public List<PatientDemographicDetail> getPatientDemographicDetails() {
        return patientDemographicDetailService.getPatientDemographicDetailsRest();
    }

    @GetMapping("/getOne")
    public PatientDemographicDetail getPatientDemographicDetail(@RequestParam Integer id) {
        return patientDemographicDetailService.getPatientDemographicDetailRest(id);
    }

    @PostMapping("/insert")
    public PatientDemographicDetail addPatientDemographicDetail(@Valid @RequestBody PatientDemographicDetail patientDemographicDetail) {
        return patientDemographicDetailService.addPatientDemographicDetailRest(patientDemographicDetail);
    }
}
