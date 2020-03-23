package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table
public class ValidityList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sampleId")
    @JsonIgnoreProperties(value = {"validityList", "hibernateLazyInitializer"}, allowSetters = true)
    private Sample sample;


}
