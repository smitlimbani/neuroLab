package com.example.neuro.services;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.Sample;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReceivingStationService {

    @Autowired
    JsonService jsonService;
    @Autowired
    MasterService masterService;
    @Autowired
    ValidityListService validityListService;
    @Autowired
    VariableService variableService;

    public String getNextXULIDRest(String sampleType){
        return sampleType+"XU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("iCount")));
    }

    public String getNextIULIDRest(String sampleType){
        return sampleType+"AU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("xCount")));
    }



    /*Below function is for generating the sample list of samples that are active, valid(fully or partially)
    and are unprocessed ie. in RECEIVED and NOT_RECEIVED state only. Once we retrieve the required master objects
    we sort them on the basis of UHID before returning them.
    Request-    Nothing
    Response-   "masters":list of masters
    */
    public String getUnprocessedSampleListRest() throws JsonProcessingException {
        List<StatusEnum> list= new LinkedList<>();

        list.add(StatusEnum.NOT_RECEIVED);
        list.add(StatusEnum.RECEIVED);
        List<Master> masters= masterService.findByIsActiveTrueAndIsValidNotAndStatusInRest(IsValidEnum.N,list);
        Collections.sort(masters);//list is sorted on the basis of master.patientDemographicDetail.uhid

        return jsonService.toJson(masters,"masters");
    }



    /*Below func is for confirming that a sample has not been received and adding it to the validity list.
    prerequisite-sample isActive status to be checked at frontend
    request-    "mId":pk of master obj
    response-    ok
     */
    public String confirmSampleNotReceivedRest(String jsonString) throws JsonProcessingException {
        Integer mId = (Integer) jsonService.fromJson(jsonString,"mId", Integer.class);

        Master master =masterService.getMasterRest(mId);
        master.setIsValid(IsValidEnum.N);
        masterService.updateMasterRest(master);
        List<Sample> sampleList= master.getSamples();
        for(Sample sample: sampleList)
            validityListService.addValidityListRest(sample.getId());

        return "ok";
    }



    /*Below function id for linking two samples where sample1-NOT_RECEIVED and sample2-RECEIVED or PROCESSED.
    It is assumed that their valid and active status is checked at frontend and both are of same sampleType.
    request:    "mID":masterId of sample1
                "ulid": ulid of sample2
    response:   "master":master object of sample1 required for generating sticker.
     */
    public String linkSamplesRest(String jsonString) throws JsonProcessingException {
        Integer mId= (Integer)jsonService.fromJson(jsonString, "mId", Integer.class);
        String ulid = (String) jsonService.fromJson(jsonString, "ulid", String.class);

        Master master= masterService.getMasterRest(mId);
        //Ulid is generated for the linked sample and required fields are updated
        master.setULID(this.getNextIULIDRest(master.getSampleType()+""));
        variableService.incrementCounterRest("iCount");
        master.setLinked(ulid);
        master.setStatus(StatusEnum.RECEIVED);
        masterService.updateMasterRest(master);

        //removing unwanted data from response
        master.setPayments(null);
        master.setVials(null);
        return jsonService.toJson(master,"master");
    }



    /*The below function is use for merging two samples. Data of second sample is copied into the first one after which second
    one is deactivated.
     */
    public String mergeSamplesRest(String jsonString) throws JsonProcessingException {
        Master master1= masterService.getMasterRest((Integer) jsonService.fromJson(jsonString,"mId1", Integer.class));
        Master master2=masterService.getMasterRest ((Integer) jsonService.fromJson(jsonString, "mId2", Integer.class));

        if(master1.getULID().isEmpty()) {
            Master masterTemp = master1;
            master1 = master2;
            master2 = masterTemp;
        }
        master2.setActive(false);

        master1.setANA(master1.getANA()+master2.getANA());
        master1.setANCA(master1.getANCA() + master2.getANCA());
        master1.setGANGIGG(master1.getGANGIGG()+ master2.getGANGIGG());
        master1.setGANGIGM(master1.getGANGIGM()+ master2.getGANGIGM());
        master1.setMOG(master1.getMOG()+ master1.getMOG());
        master1.setMYU(master1.getMYU()+ master2.getMYU());
        master1.setNMDA(master1.getNMDA()+master2.getNMDA());
        master1.setPANA(master1.getPANA()+ master2.getPANA());
        master1.setRemainingAmount(master1.getRemainingAmount()+ master2.getRemainingAmount());
        master1.setTotalAmount(master1.getTotalAmount()+ master2.getTotalAmount());

        return "";
    }
}
