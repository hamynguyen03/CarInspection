package com.carinspection.CarInspection.services;

import com.carinspection.CarInspection.exceptions.BadRequestException;
import com.carinspection.CarInspection.exceptions.NotFoundException;
import com.carinspection.CarInspection.models.CarEntity;
import com.carinspection.CarInspection.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarEntity> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<CarEntity> getCarById(Long carId) {
        return carRepository.findById(carId);
    }

    public Optional<CarEntity> getCarByName(String name) {
        return carRepository.findByName(name);
    }

    public CarEntity addNewCar(CarEntity carEntity) throws BadRequestException {
        Optional<CarEntity> existingCar = carRepository.findByName(carEntity.getName());
        if (existingCar.isPresent()) {
            throw new BadRequestException("Car with name " + carEntity.getName() + " already exists.");
        }
        return carRepository.save(carEntity);
    }

    public CarEntity updateCarStatus(String name, int status) throws NotFoundException {
        CarEntity car = carRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Car with name " + name + " not found."));
        car.setStatus(status);
        return carRepository.save(car);
    }

    public void deleteCar(String name) throws NotFoundException {
        CarEntity car = carRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Car with name " + name + " not found."));
        carRepository.deleteByName(name);
    }

    public void deleteCar(Long carId) throws NotFoundException {
        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car with ID " + carId + " not found."));
        carRepository.deleteById(carId);
    }

}
