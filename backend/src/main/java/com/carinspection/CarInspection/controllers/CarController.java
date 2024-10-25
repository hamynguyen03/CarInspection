package com.carinspection.CarInspection.controllers;

import com.carinspection.CarInspection.exceptions.BadRequestException;
import com.carinspection.CarInspection.exceptions.NotFoundException;
import com.carinspection.CarInspection.models.CarEntity;
import com.carinspection.CarInspection.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    // GET /cars: Get a list of all cars
    @GetMapping
    public ResponseEntity<?> getAllCars() throws NotFoundException {
        List<CarEntity> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            throw new NotFoundException("No cars found.");
        }
        return ResponseEntity.ok(cars);
    }

    // GET /cars/{carName}: Get a specific car by carName
    @GetMapping("/{carName}")
    public ResponseEntity<CarEntity> getCarByName(@PathVariable("carName") String carName) throws NotFoundException {
        Optional<CarEntity> car = carService.getCarByName(carName);
        if (car.isPresent()) {
            return ResponseEntity.ok(car.get());
        } else {
            throw new NotFoundException("Car with name " + carName + " not found.");
        }
    }

    // POST /cars: Add a new car
    @PostMapping
    public ResponseEntity<CarEntity> createCar(@RequestBody CarEntity car) throws BadRequestException {
        Optional<CarEntity> existingCar = carService.getCarByName(car.getName());
        if (existingCar.isPresent()) {
            throw new BadRequestException("Car with name " + car.getName() + " already exists.");
        }
        CarEntity savedCar = carService.addNewCar(car);
        return ResponseEntity.ok(savedCar);
    }

    // DELETE /cars/{carId}: Delete a car by carId
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable("carId") Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

}
