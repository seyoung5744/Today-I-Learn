package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("상품 가격에 NONE 할인정책 적용")
    @Test
    void none_discounted_product() {
        //given
        Product product = new Product("상품명", 1000, DiscountPolicy.NONE);

        //when
        int discountedPrice = product.getDiscountPrice();

        //then
        assertThat(discountedPrice).isEqualTo(1000);
    }

    @DisplayName("상품 가격에 FIX_1000 할인정책 적용")
    @Test
    void FIX_1000_discounted_product() {
        //given
        Product product = new Product("상품명", 2000, DiscountPolicy.FIX_1000_AMOUNT);

        //when
        int discountedPrice = product.getDiscountPrice();

        //then
        assertThat(discountedPrice).isEqualTo(1000);
    }
}