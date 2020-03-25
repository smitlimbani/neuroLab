package com.example.neuro.controllers;


import com.example.neuro.beans.Vial;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.SampleRepository;
import com.example.neuro.repositories.TestRepository;
import com.example.neuro.repositories.VialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("vial/")
public class VialController {
    @Autowired
    VialRepository vialRepository;
    @Autowired
    MasterRepository masterRepository;
    @Autowired
    TestRepository testRepository;

    @GetMapping("/getAll")
    public List<Vial> getVials(){
        return vialRepository.findAll();
    }

    @GetMapping("/getOne")
    public Vial getVial(@RequestParam Integer id){
        return vialRepository.getOne(id);
    }

    @PostMapping("/insert")
    public Vial addVial(@Valid @RequestBody Vial vial,@RequestParam Integer mId, @RequestParam Integer tId){
        vial.setMaster(masterRepository.getOne(mId));
        vial.setTest(testRepository.getOne(tId));
        return vialRepository.save(vial);
    }

}
