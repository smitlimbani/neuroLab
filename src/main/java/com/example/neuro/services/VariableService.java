package com.example.neuro.services;

import com.example.neuro.beans.Variable;
import com.example.neuro.repositories.VariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariableService {

    @Autowired
    private VariableRepository variableRepository;

    public List<Variable> getVariablesRest(){return variableRepository.findAll();}

    public Variable getVariableRest(Integer id){return variableRepository.getOne(id);}

    public Variable addVariableRest(Variable variable){return variableRepository.save(variable);}

    public Variable updateVariableRest(Variable variable){return variableRepository.save(variable);}

    public String getVarValRest(String varName){ return variableRepository.findByVarName(varName).getVarVal();}

    public Variable getVariableByVarNameRest(String varName){return variableRepository.findByVarName(varName);}
}
