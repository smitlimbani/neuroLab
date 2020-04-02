package com.example.neuro.services;

import com.example.neuro.beans.*;
import com.example.neuro.repositories.MasterRepository;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import com.example.neuro.utils.TestStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReceiveStationPService {

    @Autowired
    VariableService variableService;
    @Autowired
    PatientDemographicDetailService patientDemographicDetailsService;
    @Autowired
    PaymentCategoryService paymentCategoryService;
    @Autowired
    MasterService masterService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    SampleService sampleService;
    @Autowired
    ExternalSampleService externalSampleService;
    @Autowired
    JsonService jsonService;
    @Autowired
    MasterRepository masterRepository;
    @Autowired
    TestService testService;
    @Autowired
    VialService vialService;
    @Autowired
    ValidityListService validityListService;

    public String getNextXULIDRest(String sampleType){
        return sampleType+"XU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("iCount")));
    }

    public String getNextIULIDRest(String sampleType){
        return sampleType+"AU"+variableService.getVarValRest("year")+"/"+String.format("%05d",1+Integer.parseInt(variableService.getVarValRest("xCount")));
    }

    @Transactional
    public String mapExternal(String ulid, String uhid, String sampleId){
        //test it
        Master master = masterService.getMasterByULIDRest(ulid);
        master.getPatientDemographicDetail().setUHID(uhid);
        ExternalSample externalSample = new ExternalSample();
        externalSample.setExternalSampleId(sampleId);
        externalSample.setMaster(master);
        externalSampleService.addExternalSampleRest(externalSample);
        return "ok";
    }

    public String test() throws JsonProcessingException {
        List<StatusEnum> list= new LinkedList<>();

        list.add(StatusEnum.NOT_RECEIVED);
        list.add(StatusEnum.RECEIVED);
        List<Master> masters= masterRepository.findByIsActiveTrueAndIsValidNotAndStatusIn(IsValidEnum.N,list,Sort.by(Sort.Direction.ASC,"patientDemographicDetail.UHID"));

        return jsonService.toJson(masters,"masters");
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
            "samples" : [
                {
                    "sampleId" : "sid1"
                }
            ]
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

        //Increment xCount as it is assigned
        variableService.incrementCounterRest("xCount");

        //Add payment details of that transaction
        List<Payment> payments = (new JsonService<Payment>()).fromJsonList(jsonString,"payments", Payment.class);
        for(Payment payment : payments){
            payment.setMaster(master);
        }
        payments = paymentService.addPaymentsRest(payments);

        //Adding received all the samples
        List<Sample> samples = (List<Sample>)(new JsonService<Sample>()).fromJsonList(jsonString,"samples", Sample.class);
        for(Sample sample : samples){
            sample.setMaster(master);
        }
        samples = sampleService.addSamplesRest(samples);
        return "ok";
    }

    @Transactional
    public List<Vial> separateSampleRest(Master master){
        /*
        Input : Master object with updated test flag,status,isValid
        */
        Master masterDB = masterService.getMasterRest(master.getId());
        if (masterDB.getStatus() != StatusEnum.RECEIVED){
            //if it is re-printing
            return null;
        }

        masterDB.setIsValid(master.getIsValid());
        List<Sample> samples = masterDB.getSamples();
        addToValidityListRest(samples);     //entry into validity list for each sample

        if(master.getIsValid() != IsValidEnum.N){
            //if request is valid or  partially valid
            //then generate vial for each valid test marked
            return generateVialsRest(masterDB,master);
        }
        return null;
    }

    @Transactional
    public List<ValidityList> addToValidityListRest(List<Sample> samples){
        List<ValidityList> validityLists = new LinkedList<>();
        for (Sample sample: samples){
            validityLists.add(validityListService.addValidityListRest(sample.getId()));
        }
        return validityLists;
    }

    public List<Vial> generateVialsRest(Master masterDB, Master master) {
        masterDB.setStatus(StatusEnum.PROCESSING);
        List<Vial> vials = new LinkedList<>();
        if (masterDB.getANA() == TestStatusEnum.RAISED) {
            masterDB.setANA(master.getANA());
            if (master.getANA() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("ANA", masterDB));
            }
        }
        if (masterDB.getANCA() == TestStatusEnum.RAISED) {
            masterDB.setANCA(master.getANCA());
            if (master.getANCA() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("ANCA", masterDB));
            }
        }
        if (masterDB.getMOG() == TestStatusEnum.RAISED) {
            masterDB.setMOG(master.getMOG());
            if (master.getMOG() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("MOG", masterDB));
            }
        }
        if (masterDB.getNMDA() == TestStatusEnum.RAISED) {
            masterDB.setNMDA(master.getNMDA());
            if (master.getNMDA() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("NMDA", masterDB));
            }
        }
        if (masterDB.getPANA() == TestStatusEnum.RAISED) {
            masterDB.setPANA(master.getPANA());
            if (master.getPANA() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("PANA", masterDB));
            }
        }
        if (masterDB.getMYU() == TestStatusEnum.RAISED) {
            masterDB.setMYU(master.getMYU());
            if (master.getMYU() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("MYU", masterDB));
            }
        }
        if (masterDB.getGANGIGG() == TestStatusEnum.RAISED) {
            masterDB.setGANGIGG(master.getGANGIGG());
            if (master.getGANGIGG() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("GANGIGG", masterDB));
            }
        }
        if (masterDB.getGANGIGM() == TestStatusEnum.RAISED) {
            masterDB.setGANGIGM(master.getGANGIGM());
            if (master.getGANGIGM() == TestStatusEnum.SEPARATED) {
                vials.add(generateVialRest("GANGIGM", masterDB));
            }
        }
        masterService.updateMasterRest(masterDB);   //updating master Object in DB with new status,isValid and test columns
        return vials;
    }

    //Generating vial for given test and master entry, add it to the table
    public Vial generateVialRest(String testName,Master master){
        Vial vial = new Vial();
        Test test = testService.getTestByNameRest(testName);
        vial.setVLID(master.getULID()+":"+test.getCode());
        vial.setMaster(master);
        vial.setTest(test);
        vialService.addVialRest(vial);
        return vial;
    }

    @Transactional
    public Test updateLockedCounterRest(String code,Integer lockedCounter){
        Test test = testService.getTestByCodeRest(code);
        test.setLockedCounter(lockedCounter);
        return testService.updateTestRest(test);
    }

    @Transactional
    public Test updateLockedCounterRest(String code,Integer lockedCounter,List<Vial> vials){
//        Half way done
//        List<Vial> vialsDB = vialService.getVialsByIdsRest();
//        if(!vials.isEmpty()){
//            vialService.updateVialsRest(vials);
//        }
        return updateLockedCounterRest(code,lockedCounter);
    }

}
