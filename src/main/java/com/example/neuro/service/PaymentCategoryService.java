package com.example.neuro.service;


import com.example.neuro.beans.PaymentCategory;
import com.example.neuro.repositories.PaymentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public PaymentCategory updatePaymentCategoryRest(PaymentCategory paymentCategory) {
        return paymentCategoryRepository.save(paymentCategory);
    }
}
