package com.example.neuro.services;

import com.example.neuro.beans.ExternalSample;
import com.example.neuro.repositories.ExternalSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalSampleService {

    @Autowired
    private ExternalSampleRepository externalSampleRepository;

    public List<ExternalSample> getExternalSamplesRest(){return externalSampleRepository.findAll();}

    public ExternalSample getExternalSampleRest(Integer id){return externalSampleRepository.getOne(id);}

//    public ExternalSample addExternalSampleRest(ExternalSample externalSample, Integer mId ){
//        externalSample.setMaster(masterRepository.getOne(mId));
//        return externalSampleRepository.save(externalSample);
//    }

    public ExternalSample addExternalSampleRest(ExternalSample externalSample){
        return externalSampleRepository.save(externalSample);
    }


    public ExternalSample updateExternalSampleRest(ExternalSample externalSample){
        externalSample.setMaster(externalSampleRepository.getOne(externalSample.getId()).getMaster());
        return externalSampleRepository.save(externalSample);
    }

}
