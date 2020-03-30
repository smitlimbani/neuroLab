package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("master/")
public class MasterController {

    @Autowired
    private MasterService masterService;

    @GetMapping("/getAll")
    public List<Master> getMasters() {
        return (new MasterService()).getMastersRest();
    }

    @GetMapping("/getOne")
    public Master getMaster(@RequestParam Integer id) {
        return masterService.getMasterRest(id);
    }

    @PostMapping("/insert")
    public Master addMaster(@Valid @RequestBody Master master, @RequestParam Integer pCatId, @RequestParam Integer pId) {
        return masterService.addMasterRest(master, pCatId, pId);
    }

}
