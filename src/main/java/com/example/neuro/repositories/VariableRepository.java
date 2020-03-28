package com.example.neuro.repositories;

import com.example.neuro.beans.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariableRepository extends JpaRepository<Variable, Integer> {
}
