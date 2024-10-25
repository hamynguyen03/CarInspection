package com.carinspection.CarInspection.controllers;

import com.carinspection.CarInspection.models.CarInspectionEntity;
import com.carinspection.CarInspection.services.CarInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car-inspections")
public class CarInspectionController {

    private final CarInspectionService carInspectionService;

    @Autowired
    public CarInspectionController(CarInspectionService carInspectionService) {
        this.carInspectionService = carInspectionService;
    }

    // GET /car-inspections: Get all car inspections
    @GetMapping
    public ResponseEntity<List<CarInspectionEntity>> getAllCarInspections() {
        List<CarInspectionEntity> inspections = carInspectionService.getAllCarInspections();
        return ResponseEntity.ok(inspections);
    }

    // GET /car-inspections/{carInspectionId}: Get a specific car inspection by ID
    @GetMapping("/{carInspectionId}")
    public ResponseEntity<CarInspectionEntity> getCarInspectionById(@PathVariable("carInspectionId") Long carInspectionId) {
        CarInspectionEntity inspection = carInspectionService.getInspectionById(carInspectionId);
        return ResponseEntity.ok(inspection);
    }

    // GET /car-inspections/{carId}: Get the list of car inspections for a specific carId
    @GetMapping("/{carId}")
    public ResponseEntity<List<CarInspectionEntity>> getInspectionsByCarId(@PathVariable("carId") Long carId) {
        List<CarInspectionEntity> inspections = carInspectionService.getInspectionsByCarId(carId);
        return ResponseEntity.ok(inspections);
    }

    // GET /car-inspections/{carName}: Get the list of car inspections for a specific carName
    @GetMapping("/{carName}")
    public ResponseEntity<List<CarInspectionEntity>> getInspectionsByCarName(@PathVariable("carName") String carName) {
        List<CarInspectionEntity> inspections = carInspectionService.getInspectionsByCarName(carName);
        return ResponseEntity.ok(inspections);
    }

    // POST /car-inspections/{carName}/{inspectionCriteriaId}: Add a car inspection for a specific carName and inspection criteria
    @PostMapping("/{carName}/{criteriaId}")
    public ResponseEntity<CarInspectionEntity> addCarInspection(
            @PathVariable("carName") String carName,
            @PathVariable("criteriaId") Long criteriaId,
            @RequestParam("isGood") boolean isGood,
            @RequestParam(value = "note", required = false) String note) {
        try {
            CarInspectionEntity inspection = carInspectionService.addCarInspectionByCarName(carName, criteriaId, isGood, note);
            return ResponseEntity.ok(inspection);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // PUT /car-inspections/{carName}/{inspectionCriteriaId}: Edit an existing car inspection
    @PutMapping("/{carName}/{criteriaId}/result")
    public ResponseEntity<CarInspectionEntity> updateCarInspectionResult(
            @PathVariable("carName") String carName,
            @PathVariable("criteriaId") Long criteriaId,
            @RequestParam("isGood") boolean isGood,
            @RequestParam(value = "note", required = false) String note) {
        try {
            CarInspectionEntity updatedInspection = carInspectionService.updateCarInspectionResult(carName, criteriaId, isGood, note);
            return ResponseEntity.ok(updatedInspection);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /car-inspections/{carId}/{carInspectionId}/result: Edit an existing car inspection
    @PutMapping("/{carId}/{carInspectionId}/result")
    public ResponseEntity<CarInspectionEntity> updateCarInspection(
            @PathVariable("carId") Long carId,
            @PathVariable("carInspectionId") Long inspectionId,
            @RequestParam("isGood") boolean isGood,
            @RequestParam(value = "note", required = false) String note) {
        CarInspectionEntity updatedInspection = carInspectionService.updateCarInspection(carId, inspectionId, isGood, note);
        return ResponseEntity.ok(updatedInspection);
    }

    // DELETE /car-inspections/{carInspectionId}
    @DeleteMapping("/{carInspectionId}")
    public ResponseEntity<Void> deleteCarInspection(@PathVariable("id") Long id) {
        try {
            carInspectionService.deleteCarInspection(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}