package com.example.neuro.repositories;

import com.example.neuro.beans.PaymentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, Integer> {
    PaymentCategory findByCode(String code);
}
