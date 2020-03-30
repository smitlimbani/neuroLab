package com.example.neuro.services;

import com.example.neuro.beans.Vial;
import com.example.neuro.repositories.VialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    public Vial addVialRest(Vial vial, Integer mId, Integer tId) {
//        vial.setMaster(masterRepository.getOne(mId));
//        vial.setTest(testRepository.getOne(tId));
//        return vialRepository.save(vial);
//    }
    public Vial updateVialRest(Vial vial) {
        return vialRepository.save(vial);
    }
}
