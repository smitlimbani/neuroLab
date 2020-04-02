package com.example.neuro.repositories;

import com.example.neuro.beans.Master;
import com.example.neuro.beans.Sample;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Integer> {
    Sample findBySampleId(String sampleId);

    List<Sample> findByMaster(Master master);
}
