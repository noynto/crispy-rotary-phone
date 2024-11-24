package com.github.noynto.crispy_rotary_phone.application.feature;

import com.github.noynto.crispy_rotary_phone.application.api.CreateCar;
import com.github.noynto.crispy_rotary_phone.infrastructure.sql.Car;
import com.github.noynto.crispy_rotary_phone.infrastructure.sql.CarRepository;
import org.springframework.stereotype.Component;

@Component
public class CarCreator implements CreateCar {
    private final CarRepository carRepository;

    public CarCreator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car create() {
        Car car = new Car();
        car.setBrand("BMW");
        car.setModel("ECONOMY");
        car.setEngine("V8");
        car.setFuelType("Electrical");
        return carRepository.save(car);
    }
}
