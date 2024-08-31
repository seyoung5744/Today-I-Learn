package com.example.productorderservice.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    private ProductService productService;

    private ProductPort productPort;

    @BeforeEach
    void setUp() {
        productPort = Mockito.mock(ProductPort.class);
        productService = new ProductService(productPort);
    }

    @DisplayName("상품 수정")
    @Test
    void updateProduct() {
        // given
        final Long productId = 1L;
        final UpdateProductRequest request = new UpdateProductRequest("상품 수정", 2000, DiscountPolicy.NONE);
        final Product product = new Product("상품명", 1000, DiscountPolicy.NONE);

        given(productPort.getProduct(productId)).willReturn(product);

        // when
        productService.updateProduct(productId, request);

        // then
        assertThat(product.getName()).isEqualTo("상품 수정");
        assertThat(product.getPrice()).isEqualTo(2000);
    }
//
//    private static class StubProductPort implements ProductPort {
//
//        public Product getProduct_will_return;
//
//        @Override
//        public void save(Product product) {
//
//        }
//
//        @Override
//        public Product getProduct(Long productId) {
//            return getProduct_will_return;
//        }
//    }
}
