package com.example.productorderservice.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.productorderservice.order.domain.Order;
import com.example.productorderservice.product.domain.DiscountPolicy;
import com.example.productorderservice.product.domain.Product;
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