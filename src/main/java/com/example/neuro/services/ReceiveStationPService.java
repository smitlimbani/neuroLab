package com.example.neuro.services;

import com.example.neuro.beans.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    PaymentService paymentService;
    @Autowired
    SampleService sampleService;

    public String getNextXULIDRest(String sampleType){
        return sampleType+"XU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("iCount")));
    }

    public String getNextIULIDRest(String sampleType){
        return sampleType+"AU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("xCount")));
    }

    public String incCounter(String counterName){
        Variable variable = variableService.getVariableByVarNameRest(counterName);
        System.out.println(variable);
        Integer no = 1+Integer.parseInt(variable.getVarVal());
        System.out.println("value");
        variable.setVarVal(no.toString());
        variableService.updateVariableRest(variable);
        return variable.getVarVal();
    }

    public String storeXPatientDetailRest(String jsonString) throws JsonProcessingException {
        /*
        remaining amount will be pre-populated from front end;
        jsonString = {
            "patientDemographicDetail" : {...},
            "paymentCategoryCode" : "ABP100",
            "master" : {...},
            "payments" : [{},{}...],
            "samples" : [{},{}...]
        }
        */
        PatientDemographicDetail patientDemographicDetail = (PatientDemographicDetail) jsonService.fromJson(jsonString,"patientDemographicDetail", PatientDemographicDetail.class);
        patientDemographicDetail = patientDemographicDetailsService.addPatientDemographicDetailRest(patientDemographicDetail);

        Master master = (Master) jsonService.fromJson(jsonString,"master",Master.class);
        String paymentCategoryCode = (String) jsonService.fromJson(jsonString,"paymentCategoryCode",String.class);
        master.setPaymentCategory(paymentCategoryService.getPaymentCategoryByCodeRest(paymentCategoryCode));
        master.setPatientDemographicDetail(patientDemographicDetail);
        master = masterService.addMasterRest(master);

        incCounter("xCounter");

        List<Payment> payments = (List<Payment>)jsonService.fromJsonList(jsonString,"payments");
        for(Payment payment : payments){
            payment.setMaster(master);
        }
        payments = paymentService.addPaymentsRest(payments);

        List<Sample> samples = (List<Sample>)jsonService.fromJsonList(jsonString,"samples");
        for(Sample sample : samples){
            sample.setMaster(master);
        }
        samples = sampleService.addSamplesRest(samples);
        return "ok";
    }
}
