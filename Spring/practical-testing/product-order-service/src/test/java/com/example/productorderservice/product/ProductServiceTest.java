package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.productorderservice.product.application.service.GetProductResponse;
import com.example.productorderservice.product.application.service.ProductService;
import com.example.productorderservice.product.application.service.UpdateProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품 수정")
    @Test
    void updateProduct() {
        // given
        productService.addProduct(ProductSteps.createAddProductRequest());
        final Long productId = 1L;
        final UpdateProductRequest request = ProductSteps.createUpdateProductRequest();

        // when
        productService.updateProduct(productId, request);

        // then
        final ResponseEntity<GetProductResponse> response = productService.getProduct(productId);
        final GetProductResponse productResponse = response.getBody();

        assertThat(productResponse.name()).isEqualTo("상품 수정");
        assertThat(productResponse.price()).isEqualTo(2000);
    }

}
