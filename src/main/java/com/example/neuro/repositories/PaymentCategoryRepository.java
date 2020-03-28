package com.example.neuro.repositories;

import com.example.neuro.beans.PaymentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, Integer> {
    PaymentCategory findByCode(String code);
}
