package com.example.neuro.repositories;

import com.example.neuro.beans.PatientDemographicDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDemographicDetailRepository extends JpaRepository<PatientDemographicDetail, Integer> {
}
