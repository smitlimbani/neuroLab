package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;

@Entity
@JsonRootName("Variable")
public class Variable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    private Integer iCount;
    private Integer xCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getICount() {
        return iCount;
    }

    public void setICount(Integer iCount) {
        this.iCount = iCount;
    }

    public Integer getXCount() {
        return xCount;
    }

    public void setXCount(Integer xCount) {
        this.xCount = xCount;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + id +
                ", iCount=" + iCount +
                ", xCount=" + xCount +
                '}';
    }

    public Variable() {
    }
}
