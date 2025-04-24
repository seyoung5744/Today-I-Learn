package com.example.catalogservice.service;

import com.example.catalogservice.jpa.Catalog;

import java.util.List;

public interface CatalogService {

    List<Catalog> getAllCatalogs();
}
