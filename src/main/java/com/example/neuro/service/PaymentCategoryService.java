package com.example.neuro.service;

import com.example.neuro.beans.Payment;
import com.example.neuro.beans.PaymentCategory;
import com.example.neuro.controllers.PaymentCategoryController;
import com.example.neuro.repositories.PaymentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PaymentCategoryService {

    @Autowired
    private PaymentCategoryRepository paymentCategoryRepository;

    public List<PaymentCategory> getPaymentCategoriesRest() {
        return paymentCategoryRepository.findAll();
    }

    public PaymentCategory getPaymentCategoryRest(Integer id) {
        return paymentCategoryRepository.getOne(id);
    }

    public PaymentCategory addPaymentCategoryRest(PaymentCategory paymentCategory) {
        return paymentCategoryRepository.save(paymentCategory);
    }
}
