package com.github.noynto.crispy_rotary_phone.infrastructure.init;

import com.github.noynto.crispy_rotary_phone.application.api.CollectCar;
import com.github.noynto.crispy_rotary_phone.infrastructure.sql.CarRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class DataCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollector.class);
    private final CarRepository carRepository;
    private final TransactionTemplate transactionTemplate;
    private final EntityManager entityManager;

    public DataCollector(CarRepository carRepository, TransactionTemplate transactionTemplate, EntityManager entityManager) {
        this.carRepository = carRepository;
        this.transactionTemplate = transactionTemplate;
        this.entityManager = entityManager;
    }

    @PostConstruct
    @Transactional(readOnly = true)
    public void collect() {
        LOGGER.info("Collecting car data...");
        CollectCar.Brand brand = new CollectCar.Brand("BMW");
        transactionTemplate.setReadOnly(true);
        transactionTemplate.executeWithoutResult(status -> {
            carRepository
                    .findByBrand(brand.name())
                    .peek(car -> LOGGER.info("REFERENCE {} BRAND {}.", car.getReference(), car.getBrand()))
                    .forEach(entityManager::detach);
        });
    }

}
