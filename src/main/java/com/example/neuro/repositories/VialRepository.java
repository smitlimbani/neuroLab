package com.example.neuro.repositories;

import com.example.neuro.beans.Test;
import com.example.neuro.beans.Vial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Repository
public interface VialRepository extends JpaRepository<Vial, Integer> {

    Vial findByVLID(String VLID);

    List<Vial> findByTestAndTestingDateOrderBySerialNo(Test test, Date testingDate);
}
