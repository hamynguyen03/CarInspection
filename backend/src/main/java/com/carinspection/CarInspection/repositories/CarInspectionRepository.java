package com.carinspection.CarInspection.repositories;

import com.carinspection.CarInspection.models.CarInspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarInspectionRepository extends JpaRepository<CarInspectionEntity, Long> {

    @Query("SELECT ci FROM CarInspectionEntity ci JOIN ci.car c WHERE c.name = :carName")
    List<CarInspectionEntity> findByCarName(@Param("carName") String carName);

    @Query("SELECT ci FROM CarInspectionEntity ci WHERE ci.car.carId = :carId")  // Fetch all inspections by carId
    List<CarInspectionEntity> findByCarId(@Param("carId") Long carId);

    @Query("SELECT ci FROM CarInspectionEntity ci WHERE ci.car.name = :name AND ci.inspectionCriteria.inspectionCriteriaId = :inspectionCriteriaId")
    Optional<CarInspectionEntity> findByCarNameAndInspectionCriteriaId(@Param("name") String name, @Param("inspectionCriteriaId") Long inspectionCriteriaId);

}

