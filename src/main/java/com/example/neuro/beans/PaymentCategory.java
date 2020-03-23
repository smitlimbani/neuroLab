package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class PaymentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentCategory", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"paymentCategory", "hibernateLazyInitializer"}, allowSetters = true)
    private Set<Master> masters;

    @Column(unique = true, nullable = false)
    private String code;

    private Double discountPercentage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Master> getMasters() {
        return masters;
    }

    public void setMasters(Set<Master> masters) {
        this.masters = masters;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "PaymentCategory{" +
                "id=" + id +
                ", master=" + masters +
                ", code='" + code + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}