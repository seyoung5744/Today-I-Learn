package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.productorderservice.ApiTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ProductApiTest extends ApiTest {

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

        final ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .get("/products/{productId}", productId)
            .then().log().all()
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo("상품명");
    }
}
