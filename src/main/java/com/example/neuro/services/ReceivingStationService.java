package com.example.neuro.services;

import com.example.neuro.beans.Master;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReceivingStationService {

    @Autowired
    JsonService jsonService;
    @Autowired
    MasterService masterService;

    /*Below function is for generating the sample list of samples that are active, valid(fully or partially)
    and are unprocessed ie. in RECEIVED and NOT_RECEIVED state only. Once we retrieve the required master objects
    we sort them on the basis of UHID before returning them.*/
    public String getUnprocessedSampleList() throws JsonProcessingException {
        List<StatusEnum> list= new LinkedList<>();
        list.add(StatusEnum.NOT_RECEIVED);
        list.add(StatusEnum.RECEIVED);
        List<Master> masters= masterService.findByIsActiveTrueAndIsValidNotAndStatusInRest(IsValidEnum.N,list);
        Collections.sort(masters);
        return jsonService.toJson(masters,"masters");
    }

//    public String confirmNotReceiveSample(String jsonString) throws JsonProcessingException {
//        Integer id = (Integer) jsonService.fromJson(jsonString,"masterId", Integer.class);
//        Master master =masterService.getMasterRest(id);
//        master.setIsValid(IsValidEnum.N);
//        masterService.updateMasterRest(master);
//
//    }


}
