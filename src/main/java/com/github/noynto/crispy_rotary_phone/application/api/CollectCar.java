package com.github.noynto.crispy_rotary_phone.application.api;

import com.github.noynto.crispy_rotary_phone.infrastructure.sql.Car;

import java.util.Objects;
import java.util.stream.Stream;

public interface CollectCar {

    /**
     * Collecter des voitures par marque.
     * @param brand la marque de la voiture
     * @return des voitures.
     */
    Stream<Car> collect(Brand brand);

    record Brand(
            String name
    ) {
        public Brand {
            Objects.requireNonNull(name, "Le nom de la marque est requis.");
        }
    }
}
