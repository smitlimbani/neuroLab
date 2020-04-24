package com.example.neuro.controllers;

import com.example.neuro.beans.Doctor;
import com.example.neuro.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("doctor/")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getOne")
    public Doctor getDoctor(@RequestParam Integer id){return doctorService.getDoctorRest(id);}

    @GetMapping("/getAll")
    public List<Doctor> getDoctors(){return doctorService.getDoctorsRest();}

    @PostMapping("/insert")
    public Doctor addDoctor(@RequestBody @Valid Doctor doctor){return doctorService.addDoctorRest(doctor);}

    @PostMapping("/update")
    public Doctor updateDoctor(@RequestBody @Valid Doctor doctor){return doctorService.updateDoctorRest(doctor);}

    @GetMapping("/delete")
    public void deleteDoctor(@RequestBody @Valid Doctor doctor){ doctorService.deleteDoctorRest(doctor);}
}
