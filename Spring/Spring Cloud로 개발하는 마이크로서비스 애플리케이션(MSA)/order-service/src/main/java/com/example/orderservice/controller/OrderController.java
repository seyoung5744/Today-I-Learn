package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.Order;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.CreateOrderRequest;
import com.example.orderservice.vo.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody CreateOrderRequest request) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(request, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createdOrderDto = orderService.createOrder(orderDto);

        OrderResponse orderResponse = mapper.map(createdOrderDto, OrderResponse.class);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderResponse);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable("userId") String userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        List<OrderResponse> result = orders.stream()
                .map(order -> new ModelMapper().map(order, OrderResponse.class))
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
