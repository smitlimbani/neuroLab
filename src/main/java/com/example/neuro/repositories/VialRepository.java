package com.example.neuro.repositories;

import com.example.neuro.beans.Vial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VialRepository extends JpaRepository<Vial, Integer> {
    List<Vial> findAllByIdIn(Collection<Integer> ids);
}
