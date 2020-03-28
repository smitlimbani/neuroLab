package com.example.neuro.controllers;

import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.service.PatientDemographicDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("patientDemographicDetail/")
public class PatientDemographicDetailController {

    private PatientDemographicDetailsService patientDemographicDetailsService = new PatientDemographicDetailsService();

    @GetMapping("/getAll")
    public List<PatientDemographicDetail> getPatientDemographicDetails() {
        return patientDemographicDetailsService.getPatientDemographicDetailsRest();
    }

    @GetMapping("/getOne")
    public PatientDemographicDetail getPatientDemographicDetail(@RequestParam Integer id) {
        return patientDemographicDetailsService.getPatientDemographicDetailRest(id);
    }

    @PostMapping("/insert")
    public PatientDemographicDetail addPatientDemographicDetail(@Valid @RequestBody PatientDemographicDetail patientDemographicDetail) {
        return patientDemographicDetailsService.addPatientDemographicDetailRest(patientDemographicDetail);
    }
}
