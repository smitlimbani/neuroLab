package com.example.neuro.services;

import com.example.neuro.beans.Sample;
import com.example.neuro.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    public List<Sample> getSamplesRest() {
        return sampleRepository.findAll();
    }

    public Sample getSampleRest(Integer id) {
        return sampleRepository.getOne(id);
    }

    public Sample addSampleRest(Sample sample) {
        return sampleRepository.save(sample);
    }

    public Sample updateSampleRest(Sample sample) {
        return sampleRepository.save(sample);
    }

//    public Sample addSampleRest(Sample sample, Integer mId) {
//        sample.setMaster(masterRepository.getOne(mId));
//        return sampleRepository.save(sample);
//    }
}
