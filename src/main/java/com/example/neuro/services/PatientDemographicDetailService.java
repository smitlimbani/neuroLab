package com.example.neuro.services;

import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.repositories.PatientDemographicDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientDemographicDetailService {

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
    public PatientDemographicDetail updatePatientDemographicDetailRest(PatientDemographicDetail patientDemographicDetail) {
        return patientDemographicDetailRepository.save(patientDemographicDetail);
    }
}
