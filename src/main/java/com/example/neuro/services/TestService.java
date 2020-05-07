package com.example.neuro.services;

import com.example.neuro.beans.Test;
import com.example.neuro.repositories.TestRepository;
import com.example.neuro.utils.TestCategoryEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.event.ListDataEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private JsonService jsonService;

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
        test.setVials(testRepository.getOne(test.getId()).getVials());
        return testRepository.save(test);
    }

    public Test getTestByNameRest(String name){
        return testRepository.findByName(name);
    }

    public Test getTestByCodeRest(String code) {
        System.out.println("getTestByCodeRest service started");
        return testRepository.findByCode(code);}

    //To active particular Test Category : IF,BLOT,OTHER. Deactivate others
    @Transactional
    public boolean resetTestsRest(TestCategoryEnum testCategoryEnum){
        List<Test> allTest = getTestsRest();
        for (Test test : allTest){
            if (test.getTestCategory() == testCategoryEnum){
                test.setActive(true);
                if(test.getLastActivDate().compareTo(Date.valueOf(LocalDate.now())) != 0){
                    test.setLockedCounter(0);
                    test.setLastActivDate(Date.valueOf(LocalDate.now()));
                }
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

    @Transactional
    public String getTestsListRest() throws JsonProcessingException {
        List<Test> tests =  testRepository.findAll();

        String listData="";
        for (Test test : tests){
            test.setVials(null);
            if(listData.length() == 0){
                listData = jsonService.toJson(test,test.getName());
            }
            else{
                listData = jsonService.toJson(test,test.getName(),listData);
            }

        }
        return listData;
    }

    public List<Test> getTestsByCategory(TestCategoryEnum testCategoryEnum){
        System.out.println("getTestsByCategory service started");
        return testRepository.findByTestCategory(testCategoryEnum);
    }
}
