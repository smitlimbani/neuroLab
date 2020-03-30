package com.example.neuro.repositories;

import com.example.neuro.beans.Variable;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface VariableRepository extends JpaRepository<Variable, Integer> {
    Variable findByVarName(String varName);
}