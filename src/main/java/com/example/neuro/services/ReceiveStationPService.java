package com.example.neuro.services;

import com.example.neuro.beans.*;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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
    @Autowired
    ExternalSampleService externalSampleService;
    @Autowired
    JsonService jsonService;
    @Autowired
    MasterRepository masterRepository;

    public String getNextXULIDRest(String sampleType){
        return sampleType+"XU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("iCount")));
    }

    public String getNextIULIDRest(String sampleType){
        return sampleType+"AU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("xCount")));
    }

    @Transactional
    public String mapExternal(String ulid, String uhid, String sampleId){
        //test it
        Master master = masterService.getMasterByULIDRest(ulid);
        master.getPatientDemographicDetail().setUHID(uhid);
        ExternalSample externalSample = new ExternalSample();
        externalSample.setExternalSampleId(sampleId);
        externalSample.setMaster(master);
        externalSampleService.addExternalSampleRest(externalSample);
        return "ok";
    }

    public String test() throws JsonProcessingException {
        List<StatusEnum> list= new LinkedList<>();

        list.add(StatusEnum.NOT_RECEIVED);
        list.add(StatusEnum.RECEIVED);
        List<Master> masters= masterRepository.findByIsActiveTrueAndIsValidNotAndStatusIn(IsValidEnum.N,list,Sort.by(Sort.Direction.ASC,"patientDemographicDetail.UHID"));

        return jsonService.toJson(masters,"masters");
    }

    @Transactional
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
        variableService.incrementCounterRest("xCount");

        //Add payment details of that transaction
        List<Payment> payments = (new JsonService<Payment>()).fromJsonList(jsonString,"payments", Payment.class);
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
        List<Sample> samples = (List<Sample>)(new JsonService<Sample>()).fromJsonList(jsonString,"samples", Sample.class);
        for(Sample sample : samples){
            sample.setMaster(master);
        }
        samples = sampleService.addSamplesRest(samples);
        return "ok";
    }
}
