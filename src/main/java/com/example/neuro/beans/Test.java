package com.example.neuro.beans;

import com.example.neuro.utils.TestCategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"test", "hibernateLazyInitializer"}, allowSetters = true)
    private Set<Vial> vials;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private Double rate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestCategoryEnum testCategoryEnum;

    @Column(nullable = false)
    private Integer groupSize;

    private Integer lockedCounter;

    @Column(nullable = false)
    private boolean isActive;

    public Set<Vial> getVials() {
        return vials;
    }

    public void setVials(Set<Vial> vials) {
        this.vials = vials;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public TestCategoryEnum getTestCategoryEnum() {
        return testCategoryEnum;
    }

    public void setTestCategoryEnum(TestCategoryEnum testCategoryEnum) {
        this.testCategoryEnum = testCategoryEnum;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public Integer getLockedCounter() {
        return lockedCounter;
    }

    public void setLockedCounter(Integer lockedCounter) {
        this.lockedCounter = lockedCounter;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", vails=" + vials +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", testCategory='" + testCategoryEnum + '\'' +
                ", groupSize=" + groupSize +
                ", lockedCounter=" + lockedCounter +
                ", isActive=" + isActive +
                '}';
    }
}
