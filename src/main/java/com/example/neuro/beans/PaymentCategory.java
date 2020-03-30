package com.example.neuro.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@JsonRootName("PaymentCategory")
public class PaymentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentCategory", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"paymentCategory", "hibernateLazyInitializer"}, allowSetters = true)
    private List<Master> masters;

    @Column(unique = true, nullable = false)
    private String code;

    private Double discountPercentage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Master> getMasters() {
        return masters;
    }

    public void setMasters(List<Master> masters) {
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
                ", code='" + code + '\'' +
                ", discountPercentage=" + discountPercentage +
                '}';
    }

    public PaymentCategory() {
    }
}
