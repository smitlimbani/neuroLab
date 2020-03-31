package com.example.neuro.services;

import com.example.neuro.beans.Master;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

//    public List<Master> findByIsActiveTrueAndIsValidNotAndStatusInRest(IsValidEnum isValidEnum, List<StatusEnum> list){
//        return masterRepository.findByIsActiveTrueAndIsValidNotAndStatusIn(isValidEnum,list);
//    }
    public List<Master> findByIsActiveTrueAndIsValidNotAndStatusInRest(IsValidEnum isValidEnum, List<StatusEnum> list, Sort sort){
        return masterRepository.findByIsActiveTrueAndIsValidNotAndStatusIn(isValidEnum,list,sort);
    }

    public Master getMasterByULIDRest(String ulid){
        return masterRepository.findByULID(ulid);
    }

    public boolean doesULIDExistBooleanRest(String ulid){
        if (masterRepository.getByULID(ulid) != null) {
            return true;
        }
        return false;
    }
    public Master doesULIDExistRest(String ulid){
        //function return pdd and master table details if ULID exist o/w NULL
        Master master =  masterRepository.findByULID(ulid);
        if (master == null) {
            return null;
        }
        master.setPaymentCategory(null);
        master.setExternalSamples(null);
        master.setPayments(null);
        master.setVials(null);
        master.setSamples(null);
        return master;
    }
}