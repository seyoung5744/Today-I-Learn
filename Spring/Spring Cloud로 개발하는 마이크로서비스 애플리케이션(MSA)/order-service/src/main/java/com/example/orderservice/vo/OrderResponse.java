package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createAt;

    private String orderId;
}
