package com.carinspection.CarInspection.repositories;

import com.carinspection.CarInspection.models.CarEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    @Query("SELECT c FROM CarEntity c WHERE c.name = :name")
    Optional<CarEntity> findByName(@Param("name") String name);

    @Query("SELECT c FROM CarEntity c WHERE c.carId = :carId")
    Optional<CarEntity> findByCarId(@Param("carId") Long carId);

    @Query("SELECT c FROM CarEntity c WHERE :name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<CarEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM CarEntity c WHERE c.name = :name")
    void deleteByName(@Param("name") String name);

}
