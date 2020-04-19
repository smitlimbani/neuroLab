package com.example.neuro.controllers;

import com.example.neuro.beans.Master;
import com.example.neuro.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rec")
public class ReceivingStationController {

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
        return receivingStationService.getNextXULIDRest(sampleType);
    }

    @GetMapping("/getNextIULID")
    public String getNextIULID(@RequestParam String sampleType) {
        System.out.println(sampleType);
        return receivingStationService.getNextIULIDRest(sampleType); }

    @GetMapping("/mapExternal")
    public String mapExternal(@RequestParam String ulid,@RequestParam String uhid,@RequestParam String sampleId){
        return receivingStationService.mapExternalRest(ulid,uhid,sampleId);
    }

    @PostMapping("/storeXPatientDetail")
    public String storeXPatientDetail(@RequestBody String jsonString) throws JsonProcessingException {
        return receivingStationService.storeXPatientDetailRest(jsonString);
    }

    @GetMapping("/getUnprocessedSampleList")
    public List<Master> getUnprocessedSampleList() {
        return receivingStationService.getUnprocessedSampleListRest();
    }

    @PostMapping("/confirmSampleNotReceived")
    public boolean confirmSampleNotReceived(@RequestBody String jsonString)throws JsonProcessingException{
        return receivingStationService.confirmSampleNotReceivedRest(jsonString);
    }

    @PostMapping("/linkSamples")
    public String linkSamples(@RequestBody String jsonString)throws JsonProcessingException{
        return receivingStationService.linkSamplesRest(jsonString);
    }

    @PostMapping("/mergeSamples")
    public String mergeSamples(@RequestBody String jsonString)throws JsonProcessingException{
        return receivingStationService.mergeSamplesRest(jsonString);
    }

    @GetMapping("/getPatientDetail")
    public String getPatientDetail(@RequestParam String sampleId)throws JsonProcessingException{
        return receivingStationService.getPatientDetailRest(sampleId);
    }

    @GetMapping("/getPatientDetailByUHID")
    public String getPatientDetailByUHID(@RequestParam String uhid)throws JsonProcessingException{
        return receivingStationService.getPatientDetailByUHIDRest(uhid);
    }

    @PostMapping("/confirmInvalidReceiving")
    public String confirmInvalidReceiving(@RequestBody String jsonString)throws JsonProcessingException{
        return receivingStationService.confirmInvalidReceivingRest(jsonString);
    }

    @GetMapping("/getLinkingULIDList")
    public String getLinkingULIDList(@RequestParam String uhid, @RequestParam String sampleType) throws JsonProcessingException {
        return receivingStationService.getLinkingULIDListRest(uhid, sampleType);
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
