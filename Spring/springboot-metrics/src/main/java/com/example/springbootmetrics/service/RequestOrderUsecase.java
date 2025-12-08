package com.example.springbootmetrics.service;

import com.example.springbootmetrics.domain.Order;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class RequestOrderUsecase {

    InMemoryRepository repository;

    public RequestOrderUsecase(InMemoryRepository repository, MeterRegistry meterRegistry) {
        this.repository = repository;
    }

    public void requestOrder() {
        Order order = Order.createRandomOrder();
        repository.insertOrder(order);
    }
}
