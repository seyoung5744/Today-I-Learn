package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.productorderservice.ApiTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class ProductApiTest extends ApiTest {

    @Autowired
    ProductRepository productRepository;

    @DisplayName("상품 등록")
    @Test
    void addProduct() {
        final var request = ProductSteps.createAddProductRequest();

        // API 요청
        final var response = ProductSteps.requestAddProduct(request);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("상품 조회")
    @Test
    void getProduct() {
        final var request = ProductSteps.createAddProductRequest();
        ProductSteps.requestAddProduct(request);
        Long productId = 1L;

        final ExtractableResponse<Response> response = ProductSteps.getProductRequest(productId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("상품명");
    }

    @DisplayName("상품 수정")
    @Test
    void updateProduct() {
        //given
        ProductSteps.requestAddProduct(ProductSteps.createAddProductRequest());
        final long productId = 1L;

        //when
        ExtractableResponse<Response> response = ProductSteps.updateProductRequest(productId);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(productRepository.findById(productId).get().getName()).isEqualTo("상품 수정");
    }

}
