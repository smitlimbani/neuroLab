package com.example.neuro.beans;

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
    private Set<Vail> vails;

    @Column(unique = true,nullable = false)
    private String code;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer rate;

    @Column(nullable = false)
    private String testCategory;

    @Column(nullable = false)
    private Integer groupSize;

    private Integer lockedCounter;

    @Column(nullable = false)
    private boolean isActive;

    public Set<Vail> getVails() {
        return vails;
    }

    public void setVails(Set<Vail> vails) {
        this.vails = vails;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getTestCategory() {
        return testCategory;
    }

    public void setTestCategory(String testCategory) {
        this.testCategory = testCategory;
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
                ", vails=" + vails +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", testCategory='" + testCategory + '\'' +
                ", groupSize=" + groupSize +
                ", lockedCounter=" + lockedCounter +
                ", isActive=" + isActive +
                '}';
    }
}
