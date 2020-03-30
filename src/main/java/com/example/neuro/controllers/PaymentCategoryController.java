package com.example.neuro.controllers;

import com.example.neuro.beans.PaymentCategory;
import com.example.neuro.services.PaymentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("paymentCategory/")
public class PaymentCategoryController {

    @Autowired
    private PaymentCategoryService paymentCategoryService;

    @GetMapping("/getAll")
    public List<PaymentCategory> getPaymentCategories() {
        return paymentCategoryService.getPaymentCategoriesRest();
    }

    @GetMapping("/getOne")
    public PaymentCategory getPaymentCategory(@RequestParam Integer id) {
        return paymentCategoryService.getPaymentCategoryRest(id);
    }

    @PostMapping("/insert")
    public PaymentCategory addPaymentCategory(@Valid @RequestBody PaymentCategory paymentCategory) {
        return paymentCategoryService.addPaymentCategoryRest(paymentCategory);
    }
}
