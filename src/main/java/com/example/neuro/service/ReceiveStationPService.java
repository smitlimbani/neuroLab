package com.example.neuro.service;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.beans.Payment;
import com.example.neuro.beans.PaymentCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveStationPService {

    @Autowired
    VariableService variableService;
    @Autowired
    PatientDemographicDetailService patientDemographicDetailsService;
    @Autowired
    JsonService jsonService;
    @Autowired
    PaymentCategoryService paymentCategoryService;
    @Autowired
    MasterService masterService;

    public String getNextXULIDRest(){
        return "XU"+variableService.getVarVal("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarVal("iCount")));
    }

    public String getNextIULIDRest(){
        return "AU"+variableService.getVarVal("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarVal("xCount")));
    }

    public void storeXPatientDetailRest(String jsonString) throws JsonProcessingException {
        PatientDemographicDetail patientDemographicDetail = (PatientDemographicDetail) jsonService.fromJson(jsonString,"patientDemographicDetail", PatientDemographicDetail.class);
        patientDemographicDetail = patientDemographicDetailsService.addPatientDemographicDetailRest(patientDemographicDetail);

        Master master = (Master) jsonService.fromJson(jsonString,"master",Master.class);
//        master.setPaymentCategory(paymentCategoryService.);
        masterService.addMasterRest(master);
    }
}
