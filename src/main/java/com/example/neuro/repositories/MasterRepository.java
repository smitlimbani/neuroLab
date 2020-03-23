package com.example.neuro.repositories;

import com.example.neuro.beans.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer> {
}
