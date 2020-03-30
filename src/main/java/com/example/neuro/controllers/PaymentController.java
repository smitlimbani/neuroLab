package com.example.neuro.controllers;

import com.example.neuro.beans.Payment;
import com.example.neuro.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("payment/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getAll")
    public List<Payment> getPayments() {
        return paymentService.getPaymentsRest();
    }

    @GetMapping("/getOne")
    public Payment getPayment(@RequestParam Integer id) {
        return paymentService.getPaymentRest(id);
    }

    @PostMapping("/insert")
    public Payment addPayment(@Valid @RequestBody Payment payment) {
        return paymentService.addPaymentRest(payment);
    }

//    @PostMapping("/insert")
//    public Payment addPayment(@Valid @RequestBody Payment payment, @RequestParam Integer mId) {
//        return paymentService.addPaymentRest(payment, mId);
//    }

}
