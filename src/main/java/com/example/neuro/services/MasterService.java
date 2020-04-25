package com.example.neuro.services;

import com.example.neuro.beans.Master;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.SampleTypeEnum;
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

    public List<Master> findBySampleTypeAndPatientDemographicDetail_UHIDAndStatusNot(SampleTypeEnum sampleTypeEnum,String uhid, StatusEnum statusEnum){
        return masterRepository.findBySampleTypeAndPatientDemographicDetail_UHIDAndStatusNot(sampleTypeEnum, uhid, statusEnum);
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
        Master masterDB= masterRepository.getOne(master.getId());

        master.setPatientDemographicDetail(masterDB.getPatientDemographicDetail());
        master.setPaymentCategory(masterDB.getPaymentCategory());
        master.setPayments(masterDB.getPayments());
        master.setExternalSample(masterDB.getExternalSample());
        master.setSamples(masterDB.getSamples());
        master.setVials(masterDB.getVials());

        return masterRepository.save(master);
    }

//    public List<Master> findByIsActiveTrueAndIsValidNotAndStatusInRest(IsValidEnum isValidEnum, List<StatusEnum> list){
//        return masterRepository.findByIsActiveTrueAndIsValidNotAndStatusIn(isValidEnum,list);
//    }
    public List<Master> findByIsActiveTrueAndIsValidNotAndPatientDemographicDetail_UHIDNotLikeAndStatusInRest(IsValidEnum isValidEnum, String notPrefix,List<StatusEnum> list, Sort sort){
//        return masterRepository.findByIsActiveTrueAndIsValidNotAndStatusIn(isValidEnum,list,sort);
        return masterRepository.findByIsActiveTrueAndIsValidNotAndPatientDemographicDetail_UHIDNotLikeAndStatusIn(isValidEnum,notPrefix,list,sort);
    }

    public Master getMasterByULIDRest(String ulid){
        return masterRepository.findByULID(ulid);
    }

    public boolean doesULIDExistBooleanRest(String ulid){
        String ulid2= (ulid.charAt(0)=='S'?'C':'S') +ulid.substring(1);
        if (masterRepository.getByULID(ulid) != null|| masterRepository.getByULID(ulid2)!=null) {
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
        master.setExternalSample(null);
        master.setPayments(null);
        master.setVials(null);
        master.setSamples(null);
        return master;
    }
}
