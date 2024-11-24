package com.github.noynto.crispy_rotary_phone.infrastructure.sql;


import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface CarRepository extends JpaRepository<Car, Long> {

    @QueryHints(value = {
            @QueryHint(name = AvailableHints.HINT_FETCH_SIZE, value = "25")
    })
    Stream<Car> findByBrand(String brand);

    @Query("select c from Car c")
    Stream<Car> streamAllBy();
}
