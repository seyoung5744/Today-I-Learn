package com.example.springaopdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LibraryTest {

    @Autowired
    Library library;

    @Test
    void test() {
        // given
        Library library = new Library();
        library.setName("도서관");

        // when
        String result = library.getName();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("도서관");
    }

    @Test
    void visitedByTest() {
        // given
        library.setName("도서관");

        User user = new User();
        user.setName("스프링");

        // when
        library.visitedBy(user);

        // then
    }
}