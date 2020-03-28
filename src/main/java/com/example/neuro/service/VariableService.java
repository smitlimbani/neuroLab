package com.example.neuro.service;

import com.example.neuro.beans.Variable;
import com.example.neuro.repositories.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VariableService {

    @Autowired
    private VariableRepository variableRepository;

    public List<Variable> getVariablesRest(){return variableRepository.findAll();}

    public Variable getVariableRest(Integer id){return variableRepository.getOne(id);}

    public Variable addVariableRest(Variable variable){return variableRepository.save(variable);}
}
