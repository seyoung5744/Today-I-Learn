package com.example.catalogservice.controller;

import com.example.catalogservice.jpa.Catalog;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.CatalogResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogResponse>> getCatalogs() {
        List<Catalog> catalogs = catalogService.getAllCatalogs();

        List<CatalogResponse> result = catalogs.stream()
                .map(user -> new ModelMapper().map(user, CatalogResponse.class))
                .toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
