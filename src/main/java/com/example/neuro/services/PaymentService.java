package com.example.neuro.services;

import com.example.neuro.beans.Payment;
import com.example.neuro.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getPaymentsRest() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentRest(Integer id) {
        return paymentRepository.getOne(id);
    }

    public Payment addPaymentRest(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> addPaymentsRest(List<Payment> payments){return paymentRepository.saveAll(payments);}

    public Payment updatePaymentRest(Payment payment) {return paymentRepository.save(payment); }

    //    public Payment addPaymentRest(Payment payment, Integer mId) {
//        payment.setMaster(masterRepository.getOne(mId));
//        return paymentRepository.save(payment);
//    }
}
