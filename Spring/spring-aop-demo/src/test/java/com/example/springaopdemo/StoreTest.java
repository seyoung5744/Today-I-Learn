package com.example.springaopdemo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StoreTest {

    @Test
    void test() {
        // given
        Store store = new Store();

        // when & then
        assertThat(store.getOperationTime()).isEqualTo("AM 08:00 ~ PM 08:00");
    }

    @Test
    void isVIPUser() {
        // given
        Store store = new Store();
        store.setVisitCountByUser(11);

        // when
        boolean result = store.isVIP(new User());

        // then
        assertThat(result).isTrue();
    }
}