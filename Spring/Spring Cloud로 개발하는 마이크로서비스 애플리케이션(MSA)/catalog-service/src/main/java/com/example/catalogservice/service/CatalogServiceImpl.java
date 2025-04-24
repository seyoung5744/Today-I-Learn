package com.example.catalogservice.service;

import com.example.catalogservice.jpa.Catalog;
import com.example.catalogservice.jpa.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
