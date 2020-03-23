package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.beans.PaymentCategory;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.PatientDemographicDetailRepository;
import com.example.neuro.repositories.PaymentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("master/")
public class MasterController {

    @Autowired
    MasterRepository masterRepository;
    @Autowired
    PaymentCategoryRepository paymentCategoryRepository;
    @Autowired
    PatientDemographicDetailRepository patientDemographicDetailRepository;


    @GetMapping("/getAll")
    public List<Master> getMasters(){
        return masterRepository.findAll();
    }

    @GetMapping("/getOne")
    public Master getMaster(@RequestParam Integer id){
        return masterRepository.getOne(id);
    }

    @PostMapping("/insert")
    public Master addMaster(@Valid @RequestBody Master master,@RequestParam Integer pCatId, @RequestParam Integer pId){
        master.setPaymentCategory(paymentCategoryRepository.getOne(pCatId));
        master.setPatientDemographicDetail(patientDemographicDetailRepository.getOne(pId));
        return masterRepository.save(master);
    }

}
