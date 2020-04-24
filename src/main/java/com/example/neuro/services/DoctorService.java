package com.example.neuro.services;

import com.example.neuro.beans.Doctor;
import com.example.neuro.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getDoctorsRest(){
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
    }

    public Doctor getDoctorRest(Integer id){ return doctorRepository.getOne(id);}

    public Doctor addDoctorRest(Doctor doctor){return doctorRepository.save(doctor);}

    public Doctor updateDoctorRest(Doctor doctor){return doctorRepository.save(doctor);}

    public void deleteDoctorRest(Doctor doctor){ doctorRepository.delete(doctor);}

}
