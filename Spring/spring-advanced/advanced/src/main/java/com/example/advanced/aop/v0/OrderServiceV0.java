package com.example.advanced.aop.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV0 {

    private final OrderRepositoryV0 orderRepository;

    public void save(String itemId) {
        orderRepository.save(itemId);
    }
}
