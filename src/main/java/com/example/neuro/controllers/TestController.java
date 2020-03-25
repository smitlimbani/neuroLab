package com.example.neuro.controllers;

import com.example.neuro.beans.Test;
import com.example.neuro.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("vial/")
public class TestController {
    @Autowired
    TestRepository testRepository;

    @GetMapping("/getAll")
    public List<Test> getTests(){
        return testRepository.findAll();
    }

    @GetMapping("/getOne")
    public Test getTest(@RequestParam Integer id){
        return testRepository.getOne(id);
    }

    @PostMapping("/insert")
    public Test addTest(@Valid @RequestBody Test test){
        return testRepository.save(test);
    }
}
