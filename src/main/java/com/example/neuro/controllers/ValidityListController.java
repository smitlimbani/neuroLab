package com.example.neuro.controllers;

import com.example.neuro.beans.ValidityList;
import com.example.neuro.services.ValidityListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("validityList/")
public class ValidityListController {

//    private ValidityListService validityListService = new ValidityListService();
    @Autowired
    private ValidityListService validityListService;

    @GetMapping("/getAll")
    public List<ValidityList> getValidityLists() {
        return validityListService.getValidityListsRest();
    }

    @GetMapping("/getOne")
    public ValidityList getValidityList(@RequestParam Integer id) {
        return validityListService.getValidityListRest(id);
    }

    @PostMapping("/insert")
    public ValidityList addValidityList(@Valid @RequestParam Integer sId) {
        return validityListService.addValidityListRest(sId);
    }

    @GetMapping("/getAllOrderByULID")
    public List<ValidityList> getValidityListsOrderByULID(){
        return validityListService.getValidityListsOrderByULIDRest();
    }

    @GetMapping("/delete")
    public boolean test(@RequestParam Integer id){
        return validityListService.deleteValidityListRest(id);
    }
}