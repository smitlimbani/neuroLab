package com.example.neuro.service;

import com.example.neuro.beans.Test;
import com.example.neuro.repositories.TestRepository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public class TestService {

    private TestRepository testRepository;

    public List<Test> getTestsRest() {
        return testRepository.findAll();
    }

    public Test getTestRest(Integer id) {
        return testRepository.getOne(id);
    }

    public Test addTestRest(@Valid @RequestBody Test test) {
        return testRepository.save(test);
    }
}
