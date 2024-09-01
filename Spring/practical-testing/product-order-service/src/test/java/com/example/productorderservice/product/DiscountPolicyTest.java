package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DiscountPolicyTest {

    @DisplayName("none 할인 정책")
    @Test
    void noneDiscountPolicy() {
        //given
        int price = 1000;

        //when
        int discountedPrice = DiscountPolicy.NONE.applyDiscount(price);

        //then
        assertThat(discountedPrice).isEqualTo(price);
    }

    @DisplayName("fix_1000 할인 정책")
    @Test
    void fix_1000_DiscountPolicy() {
        //given
        int price = 2000;

        //when
        int discountedPrice = DiscountPolicy.FIX_1000_AMOUNT.applyDiscount(price);

        //then
        assertThat(discountedPrice).isEqualTo(1000);
    }

    @DisplayName("가격 음수")
    @Test
    void over_DiscountPolicy() {
        //given
        int price = 500;

        //when
        int discountedPrice = DiscountPolicy.FIX_1000_AMOUNT.applyDiscount(price);

        //then
        assertThat(discountedPrice).isEqualTo(0);
    }
}