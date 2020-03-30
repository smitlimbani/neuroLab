package com.example.neuro.services;

import com.example.neuro.beans.Test;
import com.example.neuro.repositories.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

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

    public Test updateTestRest(Test test) {
        return testRepository.save(test);
    }
}
