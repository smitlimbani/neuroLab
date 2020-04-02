package com.example.neuro.services;

import com.example.neuro.beans.Vial;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SegregationService {

    @Autowired
    private JsonService jsonService;
    @Autowired
    private VialService vialService;


    /*Below function is for generating the current test list.
    Request-    "testCode":code of the current test
                "date": date
    Response-   "vials":list of vials
     */
    public String getCurrentTestListRest(String jsonString) throws JsonProcessingException {
        String code= (String) jsonService.fromJson(jsonString,"testCode", String.class);
        Date date=(Date) jsonService.fromJson(jsonString, "date", Date.class);
//        return jsonService.toJson(vialService.findByVLIDEndsWith(code, Sort.by(Sort.Direction.ASC,"serialNo")),"vials");
        return "ok";
    }



    /*The below function is use retrieve details of a single patient
     request:        "vlid":scanned vlid
     response:       "vial": vial object
      */
    public String getPatientDetailByVLIDRest(String jsonString)throws JsonProcessingException {
        Vial vial=  vialService.findByVLIDRest((String)jsonService.fromJson(jsonString,"vlid", String.class));

        vial.getMaster().setPayments(null);
        vial.getMaster().setVials(null);
        vial.getMaster().setSamples(null);
        vial.getMaster().setExternalSamples(null);
        vial.getMaster().setPaymentCategory(null);

        return jsonService.toJson(vial, "vial");
    }
}
