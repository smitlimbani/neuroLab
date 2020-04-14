package com.example.neuro.services;

import com.example.neuro.beans.*;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.SampleTypeEnum;
import com.example.neuro.utils.StatusEnum;
import com.example.neuro.utils.TestStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class ReceivingStationService {

    @Autowired
    private JsonService jsonService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private ValidityListService validityListService;
    @Autowired
    private VariableService variableService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    PatientDemographicDetailService patientDemographicDetailsService;
    @Autowired
    PaymentCategoryService paymentCategoryService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ExternalSampleService externalSampleService;
    @Autowired
    TestService testService;
    @Autowired
    VialService vialService;

    public String getNextXULIDRest(String sampleType){
        return sampleType+"XU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("xCount")));
    }

    public String getNextIULIDRest(String sampleType){
        return sampleType+"AU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("iCount")));
    }


    /*
    Function to map UHID and sampleId of existing external patient
    Function will insert entr data into externalSample table where we store actual sampleId of external patient
    Ulid and uhid are already verified at FE
     */
    @Transactional
    public String mapExternalRest(String ulid, String uhid, String sampleId){
        //test it
        Master master = masterService.getMasterByULIDRest(ulid);
        master.getPatientDemographicDetail().setUHID(uhid);
        ExternalSample externalSample = new ExternalSample();
        externalSample.setExternalSampleId(sampleId);
        externalSample.setMaster(master);
        externalSampleService.addExternalSampleRest(externalSample);
        return "ok";
    }

    @Transactional
    public String storeXPatientDetailRest(String jsonString) throws JsonProcessingException {

        /*
        Submitting External Patient Details
        remaining amount will be pre-populated from front end;
        jsonString = {
            "patientDemographicDetail" : {...},
            "paymentCategoryCode" : "ABP100",
            "master" : {...},
            "payments" : [{},{}...],
            "samples" : [{},{}...]
        }

        {
            "patientDemographicDetail":{
                "uhid" : "UHID143",
                "firstName" : "Vaibhav",
                "lastName" : "Dodiya",
                "sex" : "MALE"
            },
            "paymentCategoryCode" : "ABP100",
            "master" : {
                "ulid" : "CXU2040/00003",
                "nNo" : "n123456",
                "sampleType" : "S",
                "ANCA" : "RAISED",
                "ANA": "RAISED"
            },
            "payments" : [{
                "amount" : "1000",
                "details" : "from CXU2020/00001"
            },
            {
                "amount" : "200",
                "details" : "from CXU2020/00001"
            }],
            "sample" :
                {
                    "sampleId" : "sid1"
                }
        }
        */
        //add patient demoGraphic Details
        PatientDemographicDetail patientDemographicDetail = (PatientDemographicDetail) (new JsonService()).fromJson(jsonString,"patientDemographicDetail", PatientDemographicDetail.class);
        patientDemographicDetail = patientDemographicDetailsService.addPatientDemographicDetailRest(patientDemographicDetail);

        //add Master entry, before that set PDD and PaymentCategory
        Master master = (Master) (new JsonService()).fromJson(jsonString,"master",Master.class);
        String paymentCategoryCode = (String) (new JsonService()).fromJson(jsonString,"paymentCategoryCode",String.class);
        master.setPaymentCategory(paymentCategoryService.getPaymentCategoryByCodeRest(paymentCategoryCode));
        master.setPatientDemographicDetail(patientDemographicDetail);
        master = masterService.addMasterRest(master);

        String ulid = master.getULID();

        //Increment xCount as it is assigned
        variableService.incrementCounterRest("xCount",Integer.parseInt(ulid.substring(ulid.length()-5)));

        //Add payment details of that transaction
        List<Payment> payments = (new JsonService<Payment>()).fromJsonList(jsonString,"payments", Payment.class);
        for(Payment payment : payments){
            payment.setMaster(master);
        }
        payments = paymentService.addPaymentsRest(payments);

        //Adding received all the samples
        Sample sample = (Sample)(new JsonService()).fromJsonList(jsonString,"sample", Sample.class);
        sample.setMaster(master);
        sample = sampleService.addSampleRest(sample);
        return "ok";
    }





    /*Below function is for generating the sample list of samples that are active, valid(fully or partially)
    and are unprocessed ie. in RECEIVED and NOT_RECEIVED state only. Once we retrieve the required master objects
    we sort them on the basis of UHID before returning them.
    Request-    Nothing
    Response-   "masters":list of masters
    */
    public List<Master> getUnprocessedSampleListRest() {
        List<StatusEnum> list= new LinkedList<>();

        list.add(StatusEnum.NOT_RECEIVED);
        list.add(StatusEnum.RECEIVED);
        String notPrefix = "X%";
//        List<Master> masters= masterService.findByIsActiveTrueAndIsValidNotAndStatusInRest(IsValidEnum.N,list);
//        Collections.sort(masters);//list is sorted on the basis of master.patientDemographicDetail.uhid
        List<Master> masters= masterService.findByIsActiveTrueAndIsValidNotAndPatientDemographicDetail_UHIDNotLikeAndStatusInRest(IsValidEnum.N,notPrefix,list,Sort.by(Sort.Direction.ASC,"patientDemographicDetail.UHID"));
//        return jsonService.toJson(masters,"masters");
        return masters;
    }



    /*Below func is for confirming that a sample has not been received and adding it to the validity list.
    prerequisite:   sample isActive status to be checked at frontend
    request:        "mId":pk of master obj
    response:       ok
     */
    @Transactional
    public boolean confirmSampleNotReceivedRest(String jsonString) throws JsonProcessingException {
        Integer mId = (Integer) jsonService.fromJson(jsonString,"mId", Integer.class);

        Master master =masterService.getMasterRest(mId);
        master.setIsValid(IsValidEnum.N);
        master.setRemark("Not Received");
        masterService.updateMasterRest(master);
        List<Sample> sampleList= master.getSamples();
        for(Sample sample: sampleList)
            validityListService.addValidityListRest(sample.getId());
        return true;
    }



    /*Below function id for linking two samples where sample1-NOT_RECEIVED and sample2-RECEIVED or PROCESSED.
    prerequisite:   validity and isActive is checked at frontend and both are of same sampleType.
    request:        "mId":masterId of sample1
                    "ulid": ulid of sample2
    response:       "master":master object of sample1 required for generating sticker.
     */
    @Transactional
    public String linkSamplesRest(String jsonString) throws JsonProcessingException {
        Integer mId= (Integer)jsonService.fromJson(jsonString, "mId", Integer.class);
        String ulid = (String) jsonService.fromJson(jsonString, "ulid", String.class);

        Master master= masterService.getMasterRest(mId);
        //Ulid is generated for the linked sample and required fields are updated
        String ulidNext = this.getNextIULIDRest(master.getSampleType()+"");
        master.setULID(ulidNext);
        variableService.incrementCounterRest("iCount",1+Integer.parseInt(ulidNext.substring(ulid.length()-5)));
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
    @Transactional
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

        master1.setANA(selectTestStatus(master1.getANA(), master2.getANA()));
        master1.setANCA(selectTestStatus(master1.getANCA(), master2.getANCA()));
        master1.setGANGIGG(selectTestStatus(master1.getGANGIGG(), master2.getGANGIGG()));
        master1.setGANGIGM(selectTestStatus(master1.getGANGIGM(),master2.getGANGIGM()));
        master1.setMOG(selectTestStatus(master1.getMOG(),master2.getMOG()));
        master1.setMYU(selectTestStatus(master1.getMYU(),master2.getMYU()));
        master1.setNMDA(selectTestStatus(master1.getNMDA(),master2.getNMDA()));
        master1.setPANA(selectTestStatus(master1.getPANA(), master2.getPANA()));

        master1.setRemainingAmount(master1.getRemainingAmount()+ master2.getRemainingAmount());
        master1.setTotalAmount(master1.getTotalAmount()+ master2.getTotalAmount());

        masterService.updateMasterRest(master1);
        masterService.updateMasterRest(master2);

        return jsonService.toJson(master1, "master");
    }

    /*Here it is expected that the status will be only RAISED and NOT_RAISED because the samples are only
    in  RECEIVED and NOT_RECEIVED state.
     */
    public TestStatusEnum selectTestStatus(TestStatusEnum A, TestStatusEnum B){
        if(A.equals(B)) {
//            System.out.println(A+" "+ B);
            return A;
        }
        else
        {
//            System.out.println(A+" "+ B+ "set to raised");
            return TestStatusEnum.RAISED;
        }
    }



    /*The below function is use retrieve details of a single patient
    request:        "sampleId":scanned sampleId
    response:       "master": master object
     */
    @Transactional
    public String getPatientDetailRest(String jsonString)throws JsonProcessingException {
        Master master=  sampleService.findBySampleIdRest((String)jsonService.fromJson(jsonString,"sampleId", String.class)).getMaster();

        master.setVials(null);
        master.setPayments(null);
        master.setSamples(null);

        return jsonService.toJson(master, "master");
    }

    /*The below function is use retrieve details of a single patient
  request:        "uhid":scanned uhid
  response:       "pdd": pdd object
   */
    @Transactional
    public String getPatientDetailByUHIDRest(String jsonString)throws JsonProcessingException {
        PatientDemographicDetail patientDemographicDetail=  patientDemographicDetailsService.findByUHIDRest((String)jsonService.fromJson(jsonString,"uhid", String.class));

        patientDemographicDetail.setMasters(null);

        return jsonService.toJson(patientDemographicDetail, "pdd");
    }



    /* Below code is executed once user confirms invalid sample at the receiving. Ulid is added and required properties
    are updated in master and sample object. Entry is also made in the validity list.
    prerequisite:   the ulid that is sent should be valid.
    request:        "sampleId":scanned sampleId of invalid sample
                    "remark" : remark why it is marked inValid
                    "ulid":ulid that to be set for the sample
    response:       "ok"
     */
    @Transactional
    public String confirmInvalidReceivingRest(String jsonString) throws JsonProcessingException{
        Sample sample= sampleService.findBySampleIdRest((String) jsonService.fromJson(jsonString,"sampleId", String.class));
        String ulid = (String) jsonService.fromJson(jsonString,"ulid", String.class);
        String remark = (String) jsonService.fromJson(jsonString,"remark", String.class);
        //set iCount
        variableService.incrementCounterRest("iCount", Integer.parseInt(ulid.substring(ulid.length()-5)));

        //update sample and master
        Master master = sample.getMaster();
        sample.setRecDate(Date.valueOf(LocalDate.now()));
        master.setULID(ulid);
        master.setIsValid(IsValidEnum.N);
        master.setRemark(remark);
        sampleService.updateSampleRest(sample);
        masterService.updateMasterRest(master);

        //make entry in validityList
        validityListService.addValidityListRest(sample.getId());

        return "ok";
    }

    @Transactional
    public List<String> getLinkingULIDListRest(String uhid, String sampleType){
//        System.out.println("service: "+ uhid+ sampleType);
//        System.out.println(sampleType.equals("S")? SampleTypeEnum.S: SampleTypeEnum.C);
        List<Master> masters = masterService.findBySampleTypeAndPatientDemographicDetail_UHID(sampleType.equals("S")? SampleTypeEnum.S: SampleTypeEnum.C, uhid);
//        System.out.println(masters.toString());
        List<String> ulids= new ArrayList<>();
        for(Master master: masters){
            ulids.add(master.getULID());
        }
        return ulids;
    }
}
