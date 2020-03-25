package com.example.neuro.controllers;

import com.example.neuro.beans.PaymentCategory;
import com.example.neuro.repositories.PaymentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("paymentCategory/")
public class PaymentCategoryController {

    @Autowired
    PaymentCategoryRepository paymentCategoryRepository;

    @GetMapping("/getAll")
    public List<PaymentCategory> getPaymentCategories(){
        return paymentCategoryRepository.findAll();
    }

    @GetMapping("/getOne")
    public PaymentCategory getPaymentCategory(@RequestParam Integer id){
        return paymentCategoryRepository.getOne(id);
    }

    @PostMapping("/insert")
    public PaymentCategory addPaymentCategory(@Valid @RequestBody PaymentCategory paymentCategory){
        return paymentCategoryRepository.save(paymentCategory);
    }
}