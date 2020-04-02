package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonRootName("Vial")
public class Vial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "masterId")
    @JsonIgnoreProperties(value = {"vials", "hibernateLazyInitializer"}, allowSetters = true)
    private Master master;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "testId")
    @JsonIgnoreProperties(value = {"vials", "hibernateLazyInitializer"}, allowSetters = true)
    private Test test;

    @Column(unique = true, nullable = false)
    private String VLID;
    private Integer serialNo;
    private String fileName;
    private Date reportingDate;
    @Column(nullable = false)
    private Date creationDate = new Date();
    private Date testingDate;
    private String remark;
    private String result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getVLID() {
        return VLID;
    }

    public void setVLID(String VLID) {
        this.VLID = VLID;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getTestingDate() {
        return testingDate;
    }

    public void setTestingDate(Date testingDate) {
        this.testingDate = testingDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Vial{" +
                "id=" + id +
                ", master=" + master +
                ", test=" + test +
                ", VLID='" + VLID + '\'' +
                ", serialNo=" + serialNo +
                ", fileName='" + fileName + '\'' +
                ", reportingDate=" + reportingDate +
                ", creationDate=" + creationDate +
                ", testingDate=" + testingDate +
                ", remark='" + remark + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public Vial() {
    }
}
