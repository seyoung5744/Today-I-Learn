package com.example.springaopdemo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void test() {
        // given
        User user = new User();

        // when & then
        assertThat(user.greeting()).isEqualTo("hello");
    }

    @Test
    void visitTo() {
        // given
        User user = new User();
        user.setName("홍길동");

        Store store = new Store();

        // when
        user.visitTo(store);

        // then

    }
}