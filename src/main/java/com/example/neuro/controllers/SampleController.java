package com.example.neuro.controllers;

import com.example.neuro.beans.Sample;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("sample/")
public class SampleController {
    @Autowired
    SampleRepository sampleRepository;
    @Autowired
    MasterRepository masterRepository;

    @GetMapping("/getAll")
    public List<Sample> getSamples(){
        return sampleRepository.findAll();
    }

    @GetMapping("/getOne")
    public Sample getSample(@RequestParam Integer id){
        return sampleRepository.getOne(id);
    }

    @PostMapping("/insert")
    public Sample addSample(@Valid @RequestBody Sample sample,@RequestParam Integer mId){
        sample.setMaster(masterRepository.getOne(mId));
        return sampleRepository.save(sample);
    }
}
