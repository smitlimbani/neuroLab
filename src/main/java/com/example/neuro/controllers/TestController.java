package com.example.neuro.controllers;

import com.example.neuro.beans.Test;
import com.example.neuro.services.TestService;
import com.example.neuro.utils.TestCategoryEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;
import java.util.List;

@RestController
@RequestMapping("test/")
public class TestController {

    public static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @GetMapping("/getAll")
    public List<Test> getTests() {
        return testService.getTestsRest();
    }

    @GetMapping("/getOne")
    public Test getTest(@RequestParam Integer id) {
        return testService.getTestRest(id);
    }

    @PostMapping("/insert")
    public Test addTest(@Valid @RequestBody Test test) {
        return testService.addTestRest(test);
    }

    @GetMapping("/getActiveTests")
    public List<Test> getActiveTests(@RequestParam TestCategoryEnum testCategoryEnum){
        return testService.getActiveTestsRest(testCategoryEnum);
    }

    @GetMapping("/getTestsList")
    public String getTestsList() throws JsonProcessingException {
        logger.warn("...AAAaaAAH!!  He is getting all the tests..!");
        return testService.getTestsListRest();
    }

}
