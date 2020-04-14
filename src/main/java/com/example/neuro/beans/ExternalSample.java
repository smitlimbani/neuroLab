package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;

@Entity
@JsonRootName("GeneratedSample")
public class ExternalSample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "masterId")
    @JsonIgnoreProperties(value = {"externalSample", "hibernateLazyInitializer"}, allowSetters = true)
    private Master master;

    @Column(unique = true, nullable = false)
    private String ExternalSampleId;

    public String getExternalSampleId() {
        return ExternalSampleId;
    }

    public void setExternalSampleId(String externalSampleId) {
        ExternalSampleId = externalSampleId;
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

    @Override
    public String toString() {
        return "ExternalSample{" +
                "id=" + id +
                ", ExternalSampleId=" + ExternalSampleId +
                '}';
    }

    public ExternalSample() {
    }
}
