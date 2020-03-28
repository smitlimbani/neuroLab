package com.example.neuro.controllers;

import com.example.neuro.beans.Test;
import com.example.neuro.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("test/")
public class TestController {

    private TestService testService = new TestService();

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
}
