package com.example.neuro.service;

import com.example.neuro.beans.ValidityList;
import com.example.neuro.beans.Variable;
import com.example.neuro.repositories.ValidityListRepository;
import com.example.neuro.repositories.VariableRepository;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiveStationPService {

    @Autowired
    VariableService variableService;

    public String getXULIDRest(){

        String nextULIDNo = variableService.getVarVal("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarVal("iCount")));
        return nextULIDNo;
    }

    public void storeXPatientDetailRest(String jsonString){
    }
}
