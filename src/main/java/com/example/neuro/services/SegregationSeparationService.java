package com.example.neuro.services;

import com.example.neuro.beans.*;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.StatusEnum;
import com.example.neuro.utils.TestCategoryEnum;
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


    /*
     request : Master object with updated test flag,status,isValid,remark
     response: "ok" if successful, null otherwise
     */
    @Transactional
    public String separateSampleRest(Master master){
        System.out.println(master.toString());

        Master masterDB = masterService.getMasterRest(master.getId());
        if (masterDB.getStatus() == StatusEnum.RECEIVED){
            List<Sample> samples = masterDB.getSamples();
            addToValidityListRest(samples);
        }

        masterDB.setIsValid(master.getIsValid());
        masterDB.setStatus(master.getStatus());
        masterDB.setRemark(master.getRemark());

        testWiseVialModification(masterDB, master);
        masterDB.setVials(null);
        masterService.updateMasterRest(masterDB);
        return "ok";
    }

    @Transactional
    public List<ValidityList> addToValidityListRest(List<Sample> samples){
        List<ValidityList> validityLists = new LinkedList<>();
        for (Sample sample: samples){
            validityLists.add(validityListService.addValidityListRest(sample.getId()));
        }
        return validityLists;
    }

    public void testWiseVialModification(Master masterDB, Master master) {
        if(masterDB.getANA()!=master.getANA()){
            if(master.getANA()==TestStatusEnum.SEPARATED)
                generateVialRest("ANA", masterDB);
            else if(masterDB.getANA()==TestStatusEnum.SEPARATED && master.getANA()==TestStatusEnum.INVALID)
                deleteVialRest("ANA", masterDB);
            masterDB.setANA(master.getANA());
        }
        if(masterDB.getANCA()!=master.getANCA()){
            if(master.getANCA()==TestStatusEnum.SEPARATED)
                generateVialRest("ANCA", masterDB);
            else if(masterDB.getANCA()==TestStatusEnum.SEPARATED && master.getANCA()==TestStatusEnum.INVALID)
                deleteVialRest("ANCA", masterDB);
            masterDB.setANCA(master.getANCA());
        }
        if(masterDB.getMOG()!=master.getMOG()){
            if(master.getMOG()==TestStatusEnum.SEPARATED)
                generateVialRest("MOG", masterDB);
            else if(masterDB.getMOG()==TestStatusEnum.SEPARATED && master.getMOG()==TestStatusEnum.INVALID)
                deleteVialRest("MOG", masterDB);
            masterDB.setMOG(master.getMOG());
        }
        if(masterDB.getNMDA()!=master.getNMDA()){
            if(master.getNMDA()==TestStatusEnum.SEPARATED)
                generateVialRest("NMDA", masterDB);
            else if(masterDB.getNMDA()==TestStatusEnum.SEPARATED && master.getNMDA()==TestStatusEnum.INVALID)
                deleteVialRest("NMDA", masterDB);
            masterDB.setNMDA(master.getNMDA());
        }
        if(masterDB.getMYO()!=master.getMYO()){
            if(master.getMYO()==TestStatusEnum.SEPARATED)
                generateVialRest("MYO", masterDB);
            else if(masterDB.getANA()==TestStatusEnum.SEPARATED && master.getANA()==TestStatusEnum.INVALID)
                deleteVialRest("MYO", masterDB);
            masterDB.setMYO(master.getMYO());
        }
        if(masterDB.getPARA()!=master.getPARA()){
            if(master.getPARA()==TestStatusEnum.SEPARATED)
                generateVialRest("PARA", masterDB);
            else if(masterDB.getPARA()==TestStatusEnum.SEPARATED && master.getPARA()==TestStatusEnum.INVALID)
                deleteVialRest("PARA", masterDB);
            masterDB.setPARA(master.getPARA());
        }
        if(masterDB.getGANGIGG()!=master.getGANGIGG()){
            if(master.getGANGIGG()==TestStatusEnum.SEPARATED)
                generateVialRest("GANGIGG", masterDB);
            else if(masterDB.getGANGIGG()==TestStatusEnum.SEPARATED && master.getGANGIGG()==TestStatusEnum.INVALID)
                deleteVialRest("GANGIGG", masterDB);
            masterDB.setGANGIGG(master.getGANGIGG());
        }
        if(masterDB.getGANGIGM()!=master.getGANGIGM()){
            if(master.getGANGIGM()==TestStatusEnum.SEPARATED)
                generateVialRest("GANGIGM", masterDB);
            else if(masterDB.getGANGIGM()==TestStatusEnum.SEPARATED && master.getGANGIGM()==TestStatusEnum.INVALID)
                deleteVialRest("GANGIGM", masterDB);
            masterDB.setGANGIGM(master.getGANGIGG());
        }
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

    public void deleteVialRest(String testName, Master masterDB){
        for(Vial vial: masterDB.getVials()){
            System.out.println("At"+ vial.getTest().getName());
            if(testName.equalsIgnoreCase(vial.getTest().getName())) {
                System.out.println("delete"+ vial.getTest().getName());
                vialService.deleteVial(vial);
                masterDB.getVials().remove(vial);
                return;
            }
        }
//        for(Vial vial: masterDB.getVials()){
//            System.out.println("At"+ vial.getTest().getName());
//            if(testName.equalsIgnoreCase(vial.getTest().getName())) {
//                System.out.println("delete"+ vial.getTest().getName());
//                vialService.deleteVial(vial);
//            }
//
//        }
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
    public List<Vial> getTestListByCodeAndDateRest(String testCode, String date) {
//        String testCode= (String) jsonService.fromJson(jsonString,"testCode", String.class);
//        String date=(String) jsonService.fromJson(jsonString, "date", String.class);
        System.out.println("getCurrentTestListRest service started");
        Test test = testService.getTestByCodeRest(testCode);
        List<Vial> vials =  vialService.getVialsByTestAndTestingDateOrderBySerialNoRest(test,Date.valueOf(date));
        for(Vial vial:vials){
            vial.getMaster().setSamples(null);
            vial.getMaster().setVials(null);
            vial.getMaster().setPayments(null);
            vial.getMaster().setPaymentCategory(null);
            vial.getMaster().setExternalSample(null);
        }
        System.out.println("getCurrentTestListRest service ended");
        return vials;
    }

    @Transactional
    public String getTestListByCategoryAndDateRest(TestCategoryEnum testCategory, String date) throws JsonProcessingException {
        List<Test> tests = testService.getTestsByCategory(testCategory);
        List<Vial> vials;
        String listData="";
        for (Test test : tests){
            vials = vialService.getVialsByTestAndTestingDateOrderBySerialNoRest(testService.getTestByCodeRest(test.getCode()), Date.valueOf(date));
//            vials = getCurrentTestListRest(test.getCode(),date); // why the fuck this one is not working
            if(listData.length() == 0){
                listData = jsonService.toJson(vials,test.getCode());
            }
            else{
                listData = jsonService.toJson(vials,test.getCode(),listData);
            }

        }
        return listData;
    }



    /*The below function is use retrieve details of a single patient
     request:        "vlid":scanned vlid
     response:       "vial": vial object
      */
    public Vial getPatientDetailByVLIDRest(String vlid)throws JsonProcessingException {
//        Vial vial=  vialService.findByVLIDRest((String)jsonService.fromJson(jsonString,"vlid", String.class));
        Vial vial=  vialService.findByVLIDRest(vlid);

        vial.getMaster().setPayments(null);
        vial.getMaster().setVials(null);
        vial.getMaster().setSamples(null);
        vial.getMaster().setExternalSample(null);
        vial.getMaster().setPaymentCategory(null);

        return vial;
    }
}
