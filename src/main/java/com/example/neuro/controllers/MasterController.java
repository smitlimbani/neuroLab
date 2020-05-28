package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.services.MasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("master/")
public class MasterController {

    public static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private MasterService masterService;

    @GetMapping("/getAll")
    public List<Master> getMasters() {
        logger.info("getMasters called");
        return masterService.getMastersRest();
    }

    @GetMapping("/getOne")
    public Master getMaster(@RequestParam Integer id) {
        logger.info("getMaster called with id : "+id);
        return masterService.getMasterRest(id);
    }

    @PostMapping("/insert")
    public Master addMaster(@Valid @RequestBody Master master) {
        logger.info("addMaster called");
        return masterService.addMasterRest(master);
    }

    @GetMapping("/getMasterByULID")
    public Master getMasterByUlid(String ulid){
        logger.info("getMasterByUlid called with ulid "+ulid);
        return masterService.getMasterByULIDRest(ulid);
    }

    @GetMapping("/doesULIDExistBoolean")
    public boolean doesULIDExistBoolean(String ulid){
        logger.info("doesULIDExistBoolean called with ulid :"+ulid);
        return masterService.doesULIDExistBooleanRest(ulid);
    }

    @GetMapping("/getMastersByReqDateBetween")
    public List<Master> getMastersByReqDateBetween(@RequestParam Date startDate,@RequestParam Date endDate){
        logger.info("getMastersByReqDateBetween called with startDate : "+startDate+", endDate : "+endDate);
        return masterService.getMastersByReqDateBetweenRest(startDate,endDate);
    }

//    @PostMapping("/insert")
//    public Master addMaster(@Valid @RequestBody Master master, @RequestParam Integer pCId, @RequestParam Integer pDDId) {
//        return masterService.addMasterRest(master, pCId, pDDId);
//    }
}
