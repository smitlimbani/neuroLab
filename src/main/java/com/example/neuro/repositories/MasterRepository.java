package com.example.neuro.repositories;

import com.example.neuro.beans.Master;
import com.example.neuro.utils.IsValidEnum;
import com.example.neuro.utils.SampleTypeEnum;
import com.example.neuro.utils.StatusEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MasterRepository extends JpaRepository<Master, Integer> {

    Master findByULID(String ulid);

    List<Master> findByIsActiveTrueAndIsValidNotAndStatusIn(IsValidEnum isValidEnum, Collection<StatusEnum> statusEnums);

    List<Master> findByIsActiveTrueAndIsValidNotAndPatientDemographicDetail_UHIDNotLikeAndStatusIn(IsValidEnum isValidEnum,String uhid, Collection<StatusEnum> statusEnums, Sort sort);

    Master getByULID(String ulid);

    List<Master> findBySampleTypeAndPatientDemographicDetail_UHID(SampleTypeEnum sampleTypeEnum, String UHID);
}
