package com.example.neuro.service;

import com.example.neuro.beans.Payment;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MasterRepository masterRepository;

    public List<Payment> getPaymentsRest() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentRest(Integer id) {
        return paymentRepository.getOne(id);
    }

    public Payment addPaymentRest(Payment payment, Integer mId) {
        payment.setMaster(masterRepository.getOne(mId));
        return paymentRepository.save(payment);
    }
}
