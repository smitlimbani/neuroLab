package com.example.neuro.repositories;

import com.example.neuro.beans.ExternalSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalSampleRepository extends JpaRepository<ExternalSample, Integer> {
}
