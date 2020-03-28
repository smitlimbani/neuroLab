package com.example.neuro.service;

import com.example.neuro.beans.Master;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.PatientDemographicDetailRepository;
import com.example.neuro.repositories.PaymentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MasterService {

    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private PaymentCategoryRepository paymentCategoryRepository;
    @Autowired
    private PatientDemographicDetailRepository patientDemographicDetailRepository;

    public List<Master> getMastersRest() {
        return masterRepository.findAll();
    }

    public Master getMasterRest(Integer id) {
        return masterRepository.getOne(id);
    }

    public Master addMasterRest(Master master, Integer pCatId, Integer pId) {
        master.setPaymentCategory(paymentCategoryRepository.getOne(pCatId));
        master.setPatientDemographicDetail(patientDemographicDetailRepository.getOne(pId));
        return masterRepository.save(master);
    }

    public Master updateMasterRest(Master master) {
        return masterRepository.save(master);
    }

}
