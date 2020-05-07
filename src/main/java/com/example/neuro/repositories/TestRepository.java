package com.example.neuro.repositories;

import com.example.neuro.beans.Test;
import com.example.neuro.utils.TestCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    Test findByName(String name);

    Test findByCode(String code);

    List<Test> findByTestCategory(TestCategoryEnum testCategoryEnum);

}
