package com.example.neuro.controllers;

import com.example.neuro.beans.Doctor;
import com.example.neuro.services.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("doctor/")
public class DoctorController {

    public static Logger logger = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getOne")
    public Doctor getDoctor(@RequestParam Integer id){
        logger.info("getDoctor called with id : "+id);
        return doctorService.getDoctorRest(id);}


    @GetMapping("/getAll")
    public List<Doctor> getDoctors(){
        logger.info("getDoctors called");
        return doctorService.getDoctorsRest();}

    @PostMapping("/insert")
    public Doctor addDoctor(@RequestBody @Valid Doctor doctor){
        return doctorService.addDoctorRest(doctor);}

    @PostMapping("/update")
    public Doctor updateDoctor(@RequestBody @Valid Doctor doctor){return doctorService.updateDoctorRest(doctor);}

    @GetMapping("/delete")
    public void deleteDoctor(@RequestBody @Valid Doctor doctor){ doctorService.deleteDoctorRest(doctor);}
}
