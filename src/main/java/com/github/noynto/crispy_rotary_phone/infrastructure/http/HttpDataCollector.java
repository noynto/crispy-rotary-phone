package com.github.noynto.crispy_rotary_phone.infrastructure.http;

import com.github.noynto.crispy_rotary_phone.application.api.CollectCar;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HttpDataCollector {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final TransactionTemplate transactionTemplate;
    private final EntityManager entityManager;
    private final CollectCar collectCar;

    public HttpDataCollector(TransactionTemplate transactionTemplate, EntityManager entityManager, CollectCar collectCar) {
        this.transactionTemplate = transactionTemplate;
        this.entityManager = entityManager;
        this.collectCar = collectCar;
    }

    @GetMapping("response-body-emitter")
    public ResponseEntity<ResponseBodyEmitter> responseBodyEmitter() {
        ResponseBodyEmitter bodyEmitter = new ResponseBodyEmitter();
        executorService.execute(() -> {
            transactionTemplate.setReadOnly(true);
            transactionTemplate.executeWithoutResult(status -> {
                CollectCar.Brand brand = new CollectCar.Brand("BMW");
                collectCar.collect(brand)
                        .peek(car -> {
                            try {
                                bodyEmitter.send(car, MediaType.APPLICATION_JSON);
                            } catch (IOException e) {
                                bodyEmitter.completeWithError(e);
                            }
                        })
                        .forEach(entityManager::detach);
                bodyEmitter.complete();
            });
        });
        return new ResponseEntity<>(bodyEmitter, HttpStatus.OK);
    }

}
