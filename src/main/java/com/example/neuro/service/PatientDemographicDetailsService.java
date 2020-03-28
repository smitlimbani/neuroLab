package com.example.neuro.service;

import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.repositories.PatientDemographicDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PatientDemographicDetailsService {

    @Autowired
    private PatientDemographicDetailRepository patientDemographicDetailRepository;

    public List<PatientDemographicDetail> getPatientDemographicDetailsRest() {
        return patientDemographicDetailRepository.findAll();
    }

    public PatientDemographicDetail getPatientDemographicDetailRest(Integer id) {
        return patientDemographicDetailRepository.getOne(id);
    }

    public PatientDemographicDetail addPatientDemographicDetailRest(PatientDemographicDetail patientDemographicDetail) {
        return patientDemographicDetailRepository.save(patientDemographicDetail);
    }
}
