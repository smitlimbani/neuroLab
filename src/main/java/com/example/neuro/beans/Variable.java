package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;

@Entity
@JsonRootName("Variable")
public class Variable {

    public Variable() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    private String varName;
    private String varVal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarVal() {
        return varVal;
    }

    public void setVarVal(String varVal) {
        this.varVal = varVal;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + id +
                ", varName='" + varName + '\'' +
                ", varVal='" + varVal + '\'' +
                '}';
    }
}
