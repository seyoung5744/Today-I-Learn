package com.example.productorderservice.payment;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.productorderservice.ApiTest;
import com.example.productorderservice.order.OrderSteps;
import com.example.productorderservice.product.ProductSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class PaymentApiTest extends ApiTest {

    @DisplayName("상품 주문")
    @Test
    void orderProduct() {
        // given
        ProductSteps.requestAddProduct(ProductSteps.createAddProductRequest());
        OrderSteps.productOrderRequest(OrderSteps.createOrderRequest());
        final var request = PaymentSteps.createPaymentRequest();

        // when
        final var response = PaymentSteps.paymentRequest(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
