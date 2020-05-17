package com.example.neuro.services;

import com.example.neuro.beans.Test;
import com.example.neuro.beans.Vial;
import com.example.neuro.repositories.VialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.util.List;

@Service
public class VialService {
    @Autowired
    VialRepository vialRepository;

    public List<Vial> getVialsRest() {
        return vialRepository.findAll();
    }

    public Vial getVialRest(Integer id) {
        return vialRepository.getOne(id);
    }

    public Vial addVialRest(Vial vial) {
        return vialRepository.save(vial);
    }

    public void deleteVialByIdRest(Integer id){
        vialRepository.deleteById(id);
    }

    public void deleteVial(Vial vial){
        vial.setMaster(null);
        vial.setTest(null);
        vialRepository.delete(vial);}

    //    public Vial addVialRest(Vial vial, Integer mId, Integer tId) {
//        vial.setMaster(masterRepository.getOne(mId));
//        vial.setTest(testRepository.getOne(tId));
//        return vialRepository.save(vial);
//    }
    @Transactional
    public Vial updateVialRest(Vial vial) {
        Vial vialDB= vialRepository.getOne(vial.getId());

        vial.setMaster(vialDB.getMaster());
        vial.setTest(vialDB.getTest());

        return vialRepository.save(vial);
    }

    @Transactional
    public String updateVialsRest(List<Vial> vials) {
        for (Vial vial: vials)
            updateVialRest(vial);
        return "ok";
    }

    public List<Vial> getVialsByTestAndTestingDateOrderBySerialNoRest(Test test, Date testingDate){
        return vialRepository.findByTestAndTestingDateOrderBySerialNo(test,testingDate);
    }

    public Vial findByVLIDRest(String VLID){
        return vialRepository.findByVLID(VLID);
    }

    public List<Vial> getPendingVialsRest(){
        return vialRepository.findBySerialNoAndTestingDate(null,null);
    }
}
