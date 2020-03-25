package com.example.neuro.repositories;

import com.example.neuro.beans.ValidityList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidityListRepository extends JpaRepository<ValidityList, Integer> {
}
