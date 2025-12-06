package com.example.springbootflyway.dao;

import com.example.springbootflyway.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
