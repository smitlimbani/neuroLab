package com.example.neuro.controllers;

import com.example.neuro.beans.Payment;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("payment/")
public class PaymentController {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    MasterRepository masterRepository;

    @GetMapping("/getAll")
    public List<Payment> getPayments(){
        return paymentRepository.findAll();
    }

    @GetMapping("/getOne")
    public Payment getPayment(@RequestParam Integer id){
        return paymentRepository.getOne(id);
    }

    @PostMapping("/insert")
    public Payment addPayment(@Valid @RequestBody Payment payment,@RequestParam Integer mId){
        payment.setMaster(masterRepository.getOne(mId));
        return paymentRepository.save(payment);
    }
}
