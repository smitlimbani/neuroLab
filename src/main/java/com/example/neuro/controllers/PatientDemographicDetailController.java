package com.example.neuro.controllers;

import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.repositories.PatientDemographicDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("patientDemographicDetail/")
public class PatientDemographicDetailController {
    @Autowired
    PatientDemographicDetailRepository patientDemographicDetailRepository;

    @GetMapping("/getAll")
    public List<PatientDemographicDetail> getPatientDemographicDetails(){
        return patientDemographicDetailRepository.findAll();
    }

    @GetMapping("/getOne")
    public PatientDemographicDetail getPatientDemographicDetail(@RequestParam Integer id){
        return patientDemographicDetailRepository.getOne(id);
    }

    @PostMapping("/insert")
    public PatientDemographicDetail addPatientDemographicDetail(@Valid @RequestBody PatientDemographicDetail patientDemographicDetail){
        return patientDemographicDetailRepository.save(patientDemographicDetail);
    }
}
