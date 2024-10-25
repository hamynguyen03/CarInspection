package com.carinspection.CarInspection.repositories;

import com.carinspection.CarInspection.models.InspectionCriteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionCriteriaRepository extends JpaRepository<InspectionCriteriaEntity, Long> {

    @Query("SELECT c FROM InspectionCriteriaEntity c WHERE c.criteriaDesc = :criteriaDesc")
    List<InspectionCriteriaEntity> findByExactDescription(String criteriaDesc);

    @Query("SELECT c FROM InspectionCriteriaEntity c WHERE LOWER(c.criteriaDesc) LIKE LOWER(CONCAT('%', :criteriaDesc, '%'))")
    List<InspectionCriteriaEntity> findByDescriptionContainingIgnoreCase(String criteriaDesc);

}
