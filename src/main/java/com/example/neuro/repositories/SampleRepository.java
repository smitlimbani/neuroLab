package com.example.neuro.repositories;

import com.example.neuro.beans.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {
}
