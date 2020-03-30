package com.example.neuro.services;

import com.example.neuro.beans.Sample;
import com.example.neuro.beans.ValidityList;
import com.example.neuro.repositories.ValidityListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidityListService {
    @Autowired
    ValidityListRepository validityListRepository;
    @Autowired
    SampleService sampleService;

    public List<ValidityList> getValidityListsRest() {
        return validityListRepository.findAll();
    }

    public ValidityList getValidityListRest(Integer id) {
        return validityListRepository.getOne(id);
    }

    public ValidityList addValidityListRest(Integer sId) {
        ValidityList validityList = new ValidityList();
        validityList.setSample(sampleService.getSampleRest(sId));
        return validityListRepository.save(validityList);
    }

    public ValidityList updateValidityListRest(ValidityList validityList) {
        return validityListRepository.save(validityList);
    }

    public List<ValidityList> getValidityListsOrderByULIDRest(){
        return validityListRepository.findAll(Sort.by(Sort.Direction.ASC,"sample.master.id"));
    }

    public void deleteValidityListRest(Integer id){
        Sample sample= validityListRepository.getOne(id).getSample();
        sample.setValidityList(null);
        sampleService.updateSampleRest(sample); //changed it to update
//        sampleRepository.save(sample);
        validityListRepository.deleteById(id);
    }
}