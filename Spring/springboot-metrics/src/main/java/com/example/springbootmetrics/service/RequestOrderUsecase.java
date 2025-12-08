package com.example.springbootmetrics.service;

import com.example.springbootmetrics.domain.Order;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class RequestOrderUsecase {

    InMemoryRepository repository;
    private final Counter counter;

    public RequestOrderUsecase(InMemoryRepository repository, MeterRegistry meterRegistry) {
        counter = Counter.builder("order.count")
                .tag("app", "order")
                .register(meterRegistry);

        this.repository = repository;
    }

    // 실제로 Order 주문이 발행된 횟수,
    public void requestOrder() {
        Order order = Order.createRandomOrder();
        repository.insertOrder(order);
        counter.increment();
    }
}
