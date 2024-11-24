package com.github.noynto.crispy_rotary_phone.application.feature;

import com.github.noynto.crispy_rotary_phone.application.api.CollectCar;
import com.github.noynto.crispy_rotary_phone.infrastructure.sql.Car;
import com.github.noynto.crispy_rotary_phone.infrastructure.sql.CarRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class CarCollector implements CollectCar {

    private final CarRepository carRepository;

    public CarCollector(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Stream<Car> collect(Brand brand) {
        return carRepository.findByBrand(brand.name());
    }

}
