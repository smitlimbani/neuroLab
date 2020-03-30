package com.example.neuro.services;

import com.example.neuro.beans.GeneratedSample;
import com.example.neuro.repositories.GeneratedSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedSampleService {

    @Autowired
    private GeneratedSampleRepository generatedSampleRepository;

    public List<GeneratedSample> getGeneratedSamplesRest(){return generatedSampleRepository.findAll();}

    public GeneratedSample getGeneratedSampleRest(Integer id){return generatedSampleRepository.getOne(id);}

//    public GeneratedSample addGeneratedSampleRest(GeneratedSample generatedSample, Integer mId ){
//        generatedSample.setMaster(masterRepository.getOne(mId));
//        return generatedSampleRepository.save(generatedSample);
//    }

    public GeneratedSample addGeneratedSampleRest(GeneratedSample generatedSample){
        return generatedSampleRepository.save(generatedSample);
    }


    public GeneratedSample updateGeneratedSampleRest(GeneratedSample generatedSample ){
        return generatedSampleRepository.save(generatedSample);
    }

}
