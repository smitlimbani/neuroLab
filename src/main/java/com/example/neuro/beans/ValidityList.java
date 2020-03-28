package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;

@Entity
@JsonRootName("ValidityList")
public class ValidityList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sampleId")
    @JsonIgnoreProperties(value = {"validityList", "hibernateLazyInitializer"}, allowSetters = true)
    private Sample sample;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    @Override
    public String toString() {
        return "ValidityList{" +
                "id=" + id +
                ", sample=" + sample +
                '}';
    }

    public ValidityList() {
    }
}
