package com.example.springbootbatchsample.domain.repository;

import com.example.springbootbatchsample.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
