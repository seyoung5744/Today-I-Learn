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
}