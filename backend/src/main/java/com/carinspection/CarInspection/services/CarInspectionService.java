package com.carinspection.CarInspection.services;

import com.carinspection.CarInspection.models.CarEntity;
import com.carinspection.CarInspection.models.CarInspectionEntity;
import com.carinspection.CarInspection.models.InspectionCriteriaEntity;
import com.carinspection.CarInspection.repositories.CarInspectionRepository;
import com.carinspection.CarInspection.repositories.CarRepository;
import com.carinspection.CarInspection.repositories.InspectionCriteriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class CarInspectionService {

    private final CarInspectionRepository carInspectionRepository;
    private final CarRepository carRepository;
    private final InspectionCriteriaRepository inspectionCriteriaRepository;

    @Autowired
    public CarInspectionService(CarInspectionRepository carInspectionRepository, CarRepository carRepository,
                                InspectionCriteriaRepository inspectionCriteriaRepository) {
        this.carInspectionRepository = carInspectionRepository;
        this.carRepository = carRepository;
        this.inspectionCriteriaRepository = inspectionCriteriaRepository;
    }

    public CarInspectionEntity getInspectionById(Long carInspectionId) {
        return carInspectionRepository.findById(carInspectionId)
                .orElseThrow(() -> new RuntimeException("Car inspection not found with ID: " + carInspectionId));
    }

    public List<CarInspectionEntity> getInspectionsByCarId(Long carId) {
        return carInspectionRepository.findByCarId(carId);
    }

    public List<CarInspectionEntity> getAllCarInspections() {
        return carInspectionRepository.findAll();
    }

    public List<CarInspectionEntity> getInspectionsByCarName(String carName) {
        return carInspectionRepository.findByCarName(carName);
    }

    public CarInspectionEntity addCarInspectionByCarName(String carName, Long inspectionCriteriaId, boolean isGood, String note) {
        CarEntity car = carRepository.findByName(carName)
                .orElseThrow(() -> new RuntimeException("Car not found with name: " + carName));

        InspectionCriteriaEntity criteria = inspectionCriteriaRepository.findById(inspectionCriteriaId)
                .orElseThrow(() -> new RuntimeException("Inspection criteria not found with ID: " + inspectionCriteriaId));

        validateNoteIfCriteriaFailed(isGood, note);

        CarInspectionEntity inspection = new CarInspectionEntity();
        inspection.setCar(car);
        inspection.setInspectionCriteria(criteria);
        inspection.setGood(isGood);
        inspection.setNote(note);

        carInspectionRepository.save(inspection);

        updateCarStatus(car);

        return inspection;
    }

    public CarInspectionEntity addCarInspectionByCarID(Long carID, Long inspectionCriteriaId, boolean isGood, String note) {
        CarEntity car = carRepository.findByCarId(carID)
                .orElseThrow(() -> new RuntimeException("Car not found with ID: " + carID));

        InspectionCriteriaEntity criteria = inspectionCriteriaRepository.findById(inspectionCriteriaId)
                .orElseThrow(() -> new RuntimeException("Inspection criteria not found with ID: " + inspectionCriteriaId));

        validateNoteIfCriteriaFailed(isGood, note);

        CarInspectionEntity inspection = new CarInspectionEntity();
        inspection.setCar(car);
        inspection.setInspectionCriteria(criteria);
        inspection.setGood(isGood);
        inspection.setNote(note);

        carInspectionRepository.save(inspection);

        updateCarStatus(car);

        return inspection;
    }

    public CarInspectionEntity updateCarInspectionResult(String carName, Long inspectionCriteriaId,
                                                         boolean isGood, String note) {
        CarInspectionEntity inspection = carInspectionRepository
                .findByCarNameAndInspectionCriteriaId(carName, inspectionCriteriaId)
                .orElseThrow(() -> new RuntimeException("Car inspection not found for car: " + carName +
                        " and criteria ID: " + inspectionCriteriaId));

        validateNoteIfCriteriaFailed(isGood, note);

        inspection.setGood(isGood);
        inspection.setNote(note);

        carInspectionRepository.save(inspection);

        updateCarStatus(inspection.getCar());

        return inspection;
    }

    public CarInspectionEntity updateCarInspection(Long carId, Long inspectionId, boolean isGood, String note) {
        CarInspectionEntity inspection = carInspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new RuntimeException("Car inspection not found with ID: " + inspectionId));

        validateNoteIfCriteriaFailed(isGood, note);

        inspection.setGood(isGood);
        inspection.setNote(note);

        carInspectionRepository.save(inspection);

        // Update the car status
        updateCarStatus(inspection.getCar());

        return inspection;
    }

    public void deleteCarInspection(Long carInspectionId) {
        CarInspectionEntity inspection = carInspectionRepository.findById(carInspectionId)
                .orElseThrow(() -> new RuntimeException("Car inspection not found with ID: " + carInspectionId));
        carInspectionRepository.deleteById(carInspectionId);

        updateCarStatus(inspection.getCar());
    }

    private void updateCarStatus(CarEntity car) {
        List<CarInspectionEntity> inspections = carInspectionRepository.findByCarName(car.getName());
        long passedCount = inspections.stream().filter(CarInspectionEntity::isGood).count();
        long totalCriteriaCount = inspectionCriteriaRepository.count();

        if (passedCount == totalCriteriaCount) {
            car.setStatus(2); // All criteria passed
        } else if (passedCount > 0) {
            car.setStatus(1); // Some criteria passed
        } else {
            car.setStatus(0); // No criteria passed
        }

        carRepository.save(car);
    }

    private void validateNoteIfCriteriaFailed(boolean isGood, String note) {
        if (!isGood && (note == null || note.trim().isEmpty())) {
            throw new IllegalArgumentException("Note is required when criteria is not met.");
        }
    }

}
