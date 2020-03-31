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
        Submitting External Patient Details
        remaining amount will be pre-populated from front end;
        jsonString = {
            "patientDemographicDetail" : {...},
            "paymentCategoryCode" : "ABP100",
            "master" : {...},
            "payments" : [{},{}...],
            "samples" : [{},{}...]
        }
        */
        //add patient demoGraphic Details
        PatientDemographicDetail patientDemographicDetail = (PatientDemographicDetail) (new JsonService()).fromJson(jsonString,"patientDemographicDetail", PatientDemographicDetail.class);
        patientDemographicDetail = patientDemographicDetailsService.addPatientDemographicDetailRest(patientDemographicDetail);

        //add Master entry, before that set PDD and PaymentCategory
        Master master = (Master) (new JsonService()).fromJson(jsonString,"master",Master.class);
        String paymentCategoryCode = (String) (new JsonService()).fromJson(jsonString,"paymentCategoryCode",String.class);
        master.setPaymentCategory(paymentCategoryService.getPaymentCategoryByCodeRest(paymentCategoryCode));
        master.setPatientDemographicDetail(patientDemographicDetail);
        master = masterService.addMasterRest(master);

        //Increment xCount as it is assigned
        incCounter("xCount");

        //Add payment details of that transaction
        List<Payment> payments = (new JsonService<Payment>()).fromJsonList(jsonString,"payments");
        System.out.println(payments);
        System.out.println(payments.getClass());
        System.out.println(payments.get(1).getClass());
        System.out.println(payments.get(0).getDetails());
        payments.get(0).setDetails("abc");
        System.out.println(payments.get(0).getDetails());
        for(Payment payment : payments){
            payment.setMaster(master);
        }
        payments = paymentService.addPaymentsRest(payments);

        //Adding received all the samples
        List<Sample> samples = (List<Sample>)(new JsonService<Sample>()).fromJsonList(jsonString,"samples");
        for(Sample sample : samples){
            sample.setMaster(master);
        }
        samples = sampleService.addSamplesRest(samples);
        return "ok";
    }
}
