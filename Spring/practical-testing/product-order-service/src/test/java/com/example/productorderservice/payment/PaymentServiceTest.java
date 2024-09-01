package com.example.productorderservice.payment;

import com.example.productorderservice.order.OrderService;
import com.example.productorderservice.order.OrderSteps;
import com.example.productorderservice.product.ProductService;
import com.example.productorderservice.product.ProductSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentServiceTest {


    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @DisplayName("상품 주문")
    @Test
    void orderProduct() {
        //given
        productService.addProduct(ProductSteps.createAddProductRequest());
        orderService.createOrder(OrderSteps.createOrderRequest());
        final PaymentRequest request = PaymentSteps.createPaymentRequest();

        //when
        paymentService.payment(request);
    }

}
