package com.example.springbootmetrics.service;

import com.example.springbootmetrics.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryRepository {

    final AtomicLong totalPrice = new AtomicLong();
    final AtomicLong totalCount = new AtomicLong();

    public void insertOrder(Order order) {
        totalCount.addAndGet(order.getProductCount());
        totalPrice.addAndGet(order.getTotalPrice());
    }
}
