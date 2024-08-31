package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품 수정")
    @Test
    void update() {
        //given
        Product product = new Product("상품명", 1000, DiscountPolicy.NONE);

        //when
        product.update("상품 수정", 2000, DiscountPolicy.NONE);

        //then
        assertThat(product.getName()).isEqualTo("상품 수정");
        assertThat(product.getPrice()).isEqualTo(2000);
    }
}