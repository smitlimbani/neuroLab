package com.example.neuro.services;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.PatientDemographicDetail;
import com.example.neuro.beans.Sample;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
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
    @Autowired
    SampleService sampleService;

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
    prerequisite:   sample isActive status to be checked at frontend
    request:        "mId":pk of master obj
    response:       ok
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
    prerequisite:   validity and isActive is checked at frontend and both are of same sampleType.
    request:        "mId":masterId of sample1
                    "ulid": ulid of sample2
    response:       "master":master object of sample1 required for generating sticker.
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



    /*The below function is use for merging two samples. Data of second sample is copied into the first one after
    which second one is deactivated. The returning obj can be use for generating large sticker in cases one of the
    samples was in RECEIVED state.
    prerequisite:   Both sample should be RECEIVED/NOT_RECEIVED, active and have the same uhid.
    request:        "mId1":masterId of sample1
                    "mId2":masterId of sample2
    response:       "master": merged master obj
     */
    public String mergeSamplesRest(String jsonString) throws JsonProcessingException {
        Master master1= masterService.getMasterRest((Integer) jsonService.fromJson(jsonString,"mId1", Integer.class));
        Master master2=masterService.getMasterRest ((Integer) jsonService.fromJson(jsonString, "mId2", Integer.class));

        //Making master1 as the received sample(if any) so that ulid(is exists) is not lost and same status is carried forward..
        if(master1.getULID().isEmpty()) {
            Master masterTemp = master1;
            master1 = master2;
            master2 = masterTemp;
        }

        master2.setActive(false);

        master1.setANA(master1.getANA()|master2.getANA());
        master1.setANCA(master1.getANCA() | master2.getANCA());
        master1.setGANGIGG(master1.getGANGIGG()| master2.getGANGIGG());
        master1.setGANGIGM(master1.getGANGIGM()| master2.getGANGIGM());
        master1.setMOG(master1.getMOG()| master1.getMOG());
        master1.setMYU(master1.getMYU()| master2.getMYU());
        master1.setNMDA(master1.getNMDA()|master2.getNMDA());
        master1.setPANA(master1.getPANA()| master2.getPANA());
        master1.setRemainingAmount(master1.getRemainingAmount()+ master2.getRemainingAmount());
        master1.setTotalAmount(master1.getTotalAmount()+ master2.getTotalAmount());

        masterService.updateMasterRest(master1);
        masterService.updateMasterRest(master2);

        return jsonService.toJson(master1, "master");
    }



    /*The below function is use retrieve details of a single patient
    request:        "sampleId":scanned sampleId
    response:       "master": master object
     */
    public String getPatientDetailRest(String jsonString)throws JsonProcessingException {
        Master master=  sampleService.findBySampleIdRest((String)jsonService.fromJson(jsonString,"sampleId", String.class)).getMaster();

        master.setVials(null);
        master.setPayments(null);
        master.setSamples(null);

        return jsonService.toJson(master, "master");
    }



    /* Below code is executed once user confirms invalid sample at the receiving. Ulid is added and required properties
    are updated in master and sample object. Entry is also made in the validity list.
    prerequisite:   the ulid that is sent should be valid.
    request:        "sampleId":scanned sampleId of invalid sample
                    "ulid":ulid that to be set for the sample
    response:       "ok"
     */
    //=================================ULID COUNTER HAS TO BE INCREMENTED BY 'X' VALUE================================

    public String confirmInvalidReceivingRest(String jsonString) throws JsonProcessingException{
        Sample sample= sampleService.findBySampleIdRest((String) jsonService.fromJson(jsonString,"sampleId", String.class));
        String ulid = (String) jsonService.fromJson(jsonString,"ulid", String.class);

        //update sample and master
        Master master = sample.getMaster();
        sample.setRecDate(new Date());
        master.setULID(ulid);
        master.setIsValid(IsValidEnum.N);
        sampleService.updateSampleRest(sample);
        masterService.updateMasterRest(master);

        //make entry in validityList
        validityListService.addValidityListRest(sample.getId());

        return "ok";
    }
}
