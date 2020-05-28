package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rec")
public class ReceivingStationController {

    public static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    ReceivingStationService receivingStationService;
    @Autowired
    VariableService variableService;
    @Autowired
    MasterService masterService;
    @Autowired
    VialService vialService;

    @GetMapping("/getNextXULID")
    public String getNextXULID(@RequestParam String sampleType) throws JsonProcessingException {
        logger.info("getNextXULID called with sampleType : "+sampleType);
        return receivingStationService.getNextXULIDRest(sampleType);
    }

    @GetMapping("/getNextIULID")
    public String getNextIULID(@RequestParam String sampleType) {
        logger.info("getNextXULID called with sampleType : "+ sampleType);
        return receivingStationService.getNextIULIDRest(sampleType); }

    @GetMapping("/mapExternal")
    public String mapExternal(@RequestParam String ulid,@RequestParam String uhid,@RequestParam String sampleId){
        logger.info("mapExternal called with ulid :"+ulid+",uhid : "+uhid+", sampleId : "+sampleId);
        return receivingStationService.mapExternalRest(ulid,uhid,sampleId);
    }

    @PostMapping("/preReceiving")
    public String preReceiving(@RequestBody String jsonString) throws JsonProcessingException {
        logger.info("preReceiving called");
        return receivingStationService.preReceivingRest(jsonString);
    }

    @PostMapping("/storeXPatientDetail")
    public String storeXPatientDetail(@RequestBody String jsonString) throws JsonProcessingException {
        logger.info("storeXPatientDetail called");
        return receivingStationService.storeXPatientDetailRest(jsonString);
    }

    @GetMapping("/getUnprocessedSampleList")
    public List<Master> getUnprocessedSampleList() {
        logger.info("getUnprocessedSampleList called");
        return receivingStationService.getUnprocessedSampleListRest();
    }

    @PostMapping("/confirmSampleNotReceived")
    public boolean confirmSampleNotReceived(@RequestBody String jsonString)throws JsonProcessingException{
        logger.info("confirmSampleNotReceived called");

        return receivingStationService.confirmSampleNotReceivedRest(jsonString);
    }

    @PostMapping("/linkSamples")
    public String linkSamples(@RequestBody String jsonString)throws JsonProcessingException{
        logger.info("linkSamples called");
        return receivingStationService.linkSamplesRest(jsonString);
    }

    @PostMapping("/mergeSamples")
    public String mergeSamples(@RequestBody String jsonString)throws JsonProcessingException{
        logger.info("mergeSample called");
        return receivingStationService.mergeSamplesRest(jsonString);
    }

    @GetMapping("/getPatientDetail")
    public String getPatientDetail(@RequestParam String sampleId)throws JsonProcessingException{
        logger.info("getPatientDetail called with sampleId : "+sampleId);
        return receivingStationService.getPatientDetailRest(sampleId);
    }

    @GetMapping("/getPatientDetailByUHID")
    public String getPatientDetailByUHID(@RequestParam String uhid)throws JsonProcessingException{
        logger.info("getPatientDetailByUHID called with uhid : "+uhid);
        return receivingStationService.getPatientDetailByUHIDRest(uhid);
    }

    @PostMapping("/confirmInvalidReceiving")
    public String confirmInvalidReceiving(@RequestBody String jsonString)throws JsonProcessingException{
        logger.info("confirmInvalidReceiving called");
        return receivingStationService.confirmInvalidReceivingRest(jsonString);
    }

    @GetMapping("/getLinkingULIDList")
    public String getLinkingULIDList(@RequestParam String uhid, @RequestParam String sampleType) throws JsonProcessingException {
        logger.info("getLinkingULIDList called with uhid : "+uhid+",sampleType : "+sampleType);
        return receivingStationService.getLinkingULIDListRest(uhid, sampleType);
    }

    @PostMapping("/receiving")
    public String receiving(@RequestBody String jsonString)throws JsonProcessingException{
        logger.info("receiving called");
        return receivingStationService.receivingRest(jsonString);
    }

    @PostMapping("/test")
    public String jsonServiceTestFunc(@RequestBody @Valid String jsonString) throws JsonProcessingException {
        List<Master> masters = (new JsonService<Master>()).fromJsonList(jsonString, "masters", Master.class);
        System.out.println(masters.get(0));
        Master master= masters.get(0);
        System.out.println(master.getStatus());
        return "ok";
    }

//    @GetMapping("/getList")
//    public String getJsonList() throws JsonProcessingException {
//        List<Master> list = masterService.getMastersRest();
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonStringy = mapper.writerWithDefaultPrettyPrinter().withRootName("masters").writeValueAsString(list);
//        return jsonStringy;
//    }
}
