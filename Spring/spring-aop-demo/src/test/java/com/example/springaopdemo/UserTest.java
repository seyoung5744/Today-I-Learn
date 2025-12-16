package com.example.springaopdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserTest {

    @Autowired
    Store store;

    @Autowired
    Library library;

    @Test
    void test() {
        // given
        User user = new User();

        // when & then
        assertThat(user.greeting()).isEqualTo("hello");
    }

    @Test
    void visitToStore() {
        // given
        User user = new User();
        user.setName("홍길동2");

//        Store store = new Store();

        store.setVisitCountByUser(11);

        // when & then
        user.visitTo(store);
    }

    @Test
    void visitToLibrary() {
        // given
        library.setName("행복 도서관");

        User user = new User();
        user.setName("홍길동");

        library.setVisitCountByUser(11);
        
        // when & then
        user.visitTo(library);
    }
}