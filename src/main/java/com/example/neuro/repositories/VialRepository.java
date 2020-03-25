package com.example.neuro.repositories;

import com.example.neuro.beans.Vial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VialRepository extends JpaRepository<Vial, Integer> {
}
