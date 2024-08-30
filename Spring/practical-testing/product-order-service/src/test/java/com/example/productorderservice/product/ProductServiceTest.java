package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품 조회")
    @Test
    void getProduct() {
        // 상품 등록
        AddProductRequest addProductRequest = ProductSteps.createAddProductRequest();
        productService.addProduct(addProductRequest);
        final long productId = 1L;

        // 상품을 조회
        final GetProductResponse response = productService.getProduct(productId);

        // 상품의 응답을 검증
        assertThat(response).isNotNull();
    }

}
