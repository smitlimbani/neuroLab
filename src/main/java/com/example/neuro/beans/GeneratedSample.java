package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;

@Entity
@JsonRootName("GeneratedSample")
public class GeneratedSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "masterId")
    @JsonIgnoreProperties(value = {"generatedSamples", "hibernateLazyInitializer"}, allowSetters = true)
    private Master master;

    @Column(unique = true, nullable = false)
    private Integer generatedSampleId;

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

    public Integer getGeneratedSampleId() {
        return generatedSampleId;
    }

    public void setGeneratedSampleId(Integer generatedSampleId) {
        this.generatedSampleId = generatedSampleId;
    }

    @Override
    public String toString() {
        return "GeneratedSample{" +
                "id=" + id +
                ", master=" + master +
                ", generatedSampleId=" + generatedSampleId +
                '}';
    }

    public GeneratedSample() {
    }
}
