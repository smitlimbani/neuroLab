package com.example.neuro.beans;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@JsonRootName("Sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "masterId")
    @JsonIgnoreProperties(value = {"samples", "hibernateLazyInitializer"}, allowSetters = true)
    private Master master;

    @OneToOne(mappedBy = "sample", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"sample", "hibernateLazyInitializer"}, allowSetters = true)
    private ValidityList validityList;

    @Column(unique = true, nullable = false)
    private String sampleId;

    @Column
    private Date recDate = Date.valueOf(LocalDate.now());

    public ValidityList getValidityList() {
        return validityList;
    }

    public void setValidityList(ValidityList validityList) {
        this.validityList = validityList;
    }

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

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", master=" + master +
                ", sampleId='" + sampleId + '\'' +
                ", recDate=" + recDate +
                '}';
    }

    public Sample() {
    }
}
