package com.example.productorderservice.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.productorderservice.ApiTest;
import com.example.productorderservice.product.ProductSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class OrderApiTest extends ApiTest {

    @DisplayName("상품 주문")
    @Test
    void order() {
        //given
        ProductSteps.requestAddProduct(ProductSteps.createAddProductRequest());
        final var request = OrderSteps.createOrderRequest();

        //when
        final var response = OrderSteps.productOrderRequest(request);

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

}
