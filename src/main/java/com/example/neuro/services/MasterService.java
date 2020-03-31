package com.example.neuro.services;

import com.example.neuro.beans.Master;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterService {

    @Autowired
    private MasterRepository masterRepository;

    public List<Master> getMastersRest() {
        return masterRepository.findAll();
    }

    public Master getMasterRest(Integer id) {
        return masterRepository.getOne(id);
    }

//    public Master addMasterRest(Master master, Integer pCatId, Integer pId) {
//        master.setPaymentCategory(paymentCategoryRepository.getOne(pCatId));
//        master.setPatientDemographicDetail(patientDemographicDetailRepository.getOne(pId));
//        return masterRepository.save(master);
//    }

    public Master addMasterRest(Master master) {
        return masterRepository.save(master);
    }

    public Master updateMasterRest(Master master) {
        return masterRepository.save(master);
    }

    public List<Master> findByIsActiveTrueAndIsValidNotAndStatusInRest(IsValidEnum isValidEnum, List<StatusEnum> list){
        return masterRepository.findByIsActiveTrueAndIsValidNotAndStatusIn(isValidEnum,list);
    }


    public boolean doesULIDExistBoolean(String ULID){
        if (masterRepository.getByULID(ULID) != null) {
            return true;
        }
        return false;
    }
    public Master doesULIDExist(String ULID){
        //function return pdd and master table details if ULID exist o/w NULL
        Master master =  masterRepository.findByULID(ULID);
        if (master == null) {
            return null;
        }
        master.setPaymentCategory(null);
        master.setGeneratedSamples(null);
        master.setPayments(null);
        master.setVials(null);
        master.setSamples(null);
        return master;
    }
}