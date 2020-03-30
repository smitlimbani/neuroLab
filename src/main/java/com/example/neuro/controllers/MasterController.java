package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.services.MasterService;
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
        return masterService.getMastersRest();
    }

    @GetMapping("/getOne")
    public Master getMaster(@RequestParam Integer id) {
        return masterService.getMasterRest(id);
    }

    @PostMapping("/insert")
    public Master addMaster(@Valid @RequestBody Master master) {
        return masterService.addMasterRest(master);
    }

//    @PostMapping("/insert")
//    public Master addMaster(@Valid @RequestBody Master master, @RequestParam Integer pCId, @RequestParam Integer pDDId) {
//        return masterService.addMasterRest(master, pCId, pDDId);
//    }

}
