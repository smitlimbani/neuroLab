package com.example.neuro.beans;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;

    @ManyToOne(optional = false,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "masterId")
    @JsonIgnoreProperties(value = {"samples","hibernateLazyInitializer"},allowSetters = true)
    private Master master;

    @OneToOne(mappedBy = "sample")
    @JsonIgnoreProperties(value = {"sample", "hibernateLazyInitializer"}, allowSetters = true)
    private ValidityList validityList;


    @Column(unique = true, nullable = false)
    private String sampleId;

    @Column(nullable = false)
    private Date recDate = new Date();

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
}
