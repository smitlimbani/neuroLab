package com.example.neuro.services;

import com.example.neuro.beans.*;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import com.example.neuro.utils.TestStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class SegregationSeparationService {

    @Autowired
    private JsonService jsonService;
    @Autowired
    private VialService vialService;
    @Autowired
    private TestService testService;
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

    //Locked counter contains value till which list has been solidify
    @Transactional
    public Test updateLockedCounterRest(String code,Integer lockedCounter){
        Test test = testService.getTestByCodeRest(code);
        test.setLockedCounter(lockedCounter);
        return testService.updateTestRest(test);
    }

    @Transactional
    public Test updateLockedCounterRest(String code,Integer lockedCounter,List<Vial> vials){

        if(!vials.isEmpty()){
            vialService.updateVialsRest(vials);
        }
        return updateLockedCounterRest(code,lockedCounter);
    }



    /*Below function is for generating the current test list.
    Request-    "testCode":code of the current test
                "date": date
    Response-   "vials":list of vials
     */
    public List<Vial> getCurrentTestListRest(String jsonString) throws JsonProcessingException {
        String testCode= (String) jsonService.fromJson(jsonString,"testCode", String.class);
        String date=(String) jsonService.fromJson(jsonString, "date", String.class);
        Test test = testService.getTestByCodeRest(testCode);
        List<Vial> vials =  vialService.getVialsByTestAndTestingDateOrderBySerialNoRest(test,Date.valueOf(date));
        for(Vial vial:vials){
            vial.getMaster().setSamples(null);
            vial.getMaster().setVials(null);
            vial.getMaster().setPayments(null);
            vial.getMaster().setPaymentCategory(null);
            vial.getMaster().setExternalSample(null);
        }
        return vials;
    }



    /*The below function is use retrieve details of a single patient
     request:        "vlid":scanned vlid
     response:       "vial": vial object
      */
    public String getPatientDetailByVLIDRest(String jsonString)throws JsonProcessingException {
        Vial vial=  vialService.findByVLIDRest((String)jsonService.fromJson(jsonString,"vlid", String.class));

        vial.getMaster().setPayments(null);
        vial.getMaster().setVials(null);
        vial.getMaster().setSamples(null);
        vial.getMaster().setExternalSample(null);
        vial.getMaster().setPaymentCategory(null);

        return jsonService.toJson(vial, "vial");
    }
}
