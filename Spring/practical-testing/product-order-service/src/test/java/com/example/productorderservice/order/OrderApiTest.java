package com.example.productorderservice.order;

import com.example.productorderservice.product.ProductService;
import com.example.productorderservice.product.ProductSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class OrderApiTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @DisplayName("상품 주문")
    @Test
    void order() {
        //given
        productService.addProduct(ProductSteps.createAddProductRequest());
        final CreateOrderRequest request = createOrderRequest();

        //when
    
        //then

    }

    private static CreateOrderRequest createOrderRequest() {
        final Long productId = 1L;
        final int quantity = 2;
        return new CreateOrderRequest(productId, quantity);
    }

}
