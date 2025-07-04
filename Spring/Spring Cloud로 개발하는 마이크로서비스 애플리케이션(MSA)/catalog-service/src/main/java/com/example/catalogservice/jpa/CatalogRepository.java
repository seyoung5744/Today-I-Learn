package com.example.catalogservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByProductId(String productId);
}
