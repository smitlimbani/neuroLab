package com.example.neuro.services;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.beans.Sample;
import com.example.neuro.beans.Vial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private MasterService masterService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private PatientDemographicDetailService patientDemographicDetailsService;
    @Autowired
    private VialService vialService;

    public PatientDemographicDetail getCompleteDetailByUHIDRest(String uhid){
        return patientDemographicDetailsService.findByUHIDRest(uhid);
    }

    public Master getCompleteDetailByULIDRest(String ulid){
        return masterService.getMasterByULIDRest(ulid);
    }

    public Sample getCompleteDetailBySampleIdRest(String sampleId){
        return sampleService.findBySampleIdRest(sampleId);
    }

    public Vial getCompleteDetailByVLIDRest(String vlid){
        return vialService.findByVLIDRest(vlid);
    }

}
