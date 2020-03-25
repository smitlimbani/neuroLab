package com.example.neuro.controllers;

import com.example.neuro.beans.ValidityList;
import com.example.neuro.repositories.SampleRepository;
import com.example.neuro.repositories.ValidityListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("validitylist/")
public class ValidityListController {
    @Autowired
    ValidityListRepository validityListRepository;
    @Autowired
    SampleRepository sampleRepository;

    @GetMapping("/getAll")
    public List<ValidityList> getValidityLists(){
        return validityListRepository.findAll();
    }

    @GetMapping("/getOne")
    public ValidityList getValidityList(@RequestParam Integer id){
        return validityListRepository.getOne(id);
    }

    @PostMapping("/insert")
    public ValidityList addValidityList(@Valid @RequestParam Integer sId){
        ValidityList validityList = new ValidityList();
        validityList.setSample(sampleRepository.getOne(sId));
        return validityListRepository.save(validityList);
    }
}
