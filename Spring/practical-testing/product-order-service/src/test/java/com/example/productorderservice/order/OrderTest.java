package com.example.productorderservice.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.productorderservice.product.DiscountPolicy;
import com.example.productorderservice.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @DisplayName("상품 주문 총 가격")
    @Test
    void getTotalPrice() {
        //given
        Order order = new Order(new Product("상품명", 1000, DiscountPolicy.NONE), 2);

        //when
        int totalPrice = order.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(2000);
    }
}