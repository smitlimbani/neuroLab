package com.example.neuro.service;

import com.example.neuro.beans.Vial;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.TestRepository;
import com.example.neuro.repositories.VialRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VialService {
    @Autowired
    VialRepository vialRepository;
    @Autowired
    MasterRepository masterRepository;
    @Autowired
    TestRepository testRepository;

    public List<Vial> getVialsRest() {
        return vialRepository.findAll();
    }

    public Vial getVialRest(Integer id) {
        return vialRepository.getOne(id);
    }

    public Vial addVialRest(Vial vial, Integer mId, Integer tId) {
        vial.setMaster(masterRepository.getOne(mId));
        vial.setTest(testRepository.getOne(tId));
        return vialRepository.save(vial);
    }
}
