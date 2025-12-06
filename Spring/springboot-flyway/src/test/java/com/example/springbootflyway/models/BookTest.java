package com.example.springbootflyway.models;

import com.example.springbootflyway.ResetDatabase;
import com.example.springbootflyway.dao.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookTest extends ResetDatabase {

    @Autowired
    BookRepository bookRepository;

    @Test
    void create_success() {
        // 1. Test DB를 바라봐야 함.
        // 2. Test를 수행할 때마다, DB가 초기화되어야 함. (데이터의 상태에 따라 테스트 결과가 달라지는 것을 방지하기 위해)
        // 3. Book 개체가 DB에 생성되는지 확인.

        // given
        Book book = new Book();
        book.setId(1L);
        book.setName("flyway 정복하기");
        book.setAuthor("원저자");

        // when
        bookRepository.save(book);

        // then
        Book savedBook = bookRepository.findById(1L).get();
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getName()).isEqualTo("flyway 정복하기");
        assertThat(savedBook.getAuthor()).isEqualTo("원저자");
    }
}