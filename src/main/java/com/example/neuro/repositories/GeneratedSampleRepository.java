package com.example.neuro.repositories;

import com.example.neuro.beans.GeneratedSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedSampleRepository extends JpaRepository<GeneratedSample, Integer> {
}
