package com.example.neuro.services;

import com.example.neuro.beans.Test;
import com.example.neuro.repositories.TestRepository;
import com.example.neuro.utils.TestCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.event.ListDataEvent;
import java.util.LinkedList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getTestsRest() {
        return testRepository.findAll();
    }

    public Test getTestRest(Integer id) {
        return testRepository.getOne(id);
    }

    public Test addTestRest( Test test) {
        return testRepository.save(test);
    }

    public List<Test> addTestsRest(List<Test> tests){
        return testRepository.saveAll(tests);
    }

    public Test updateTestRest(Test test) {
        return testRepository.save(test);
    }

    public Test getTestByNameRest(String name){
        return testRepository.findByName(name);
    }

    public Test getTestByCodeRest(String code) { return testRepository.findByCode(code);}

    //To active particular Test Category : IF,BLOT,OTHER. Deactivate others
    @Transactional
    public boolean resetTestsRest(TestCategoryEnum testCategoryEnum){
        List<Test> allTest = getTestsRest();
        for (Test test : allTest){
            if (test.getTestCategory() == testCategoryEnum){
                test.setActive(true);
                test.setLockedCounter(0);
            }
            else {
                test.setActive(false);
            }
        }
        addTestsRest(allTest);
        return true;
    }

    @Transactional
    public List<Test> getActiveTestsRest(TestCategoryEnum testCategoryEnum){
        if (!resetTestsRest(testCategoryEnum)){
            return null;
        }
        List<Test> tests =  testRepository.findByTestCategory(testCategoryEnum);
        for (Test test: tests){
            test.setVials(null);
        }
        return tests;
    }
}