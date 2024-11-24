package com.github.noynto.crispy_rotary_phone.infrastructure.init;

import com.github.noynto.crispy_rotary_phone.application.api.CreateCar;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.stream.Stream;

@Component
public class DataCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCreator.class);

    private final CreateCar createCar;
    private final TransactionTemplate transactionTemplate;
    private final EntityManager entityManager;

    public DataCreator(CreateCar createCar, TransactionTemplate transactionTemplate, EntityManager entityManager) {
        this.createCar = createCar;
        this.transactionTemplate = transactionTemplate;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void create() {
        LOGGER.info("Creating data...");
        transactionTemplate.setReadOnly(false);
        transactionTemplate.executeWithoutResult(status -> {
            Stream
                    .generate(createCar::create)
                    .limit(50000)
                    .peek(entityManager::persist)
                    .forEach(entityManager::detach);
        });
        LOGGER.info("Data created");
    }

}
