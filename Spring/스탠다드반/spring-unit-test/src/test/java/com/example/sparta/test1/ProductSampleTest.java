package com.example.sparta.test1;

import com.example.sparta.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductSampleTest {

    Product product;

    @BeforeEach
    void initProduct() {
        product = new Product(1L, "티셔츠", "무지 티 팝니다.", 10000L, 100L, 0L);
    }

    @Test
    public void 상품_구매_성공() {
        product.purchased(10L);

        assertThat(product.getAmount()).isEqualTo(90L);
        assertThat(product.getSaleCount()).isEqualTo(10L);
    }

    @Test
    public void 상품_재고를_초과하는_구매_실패() {
        // 재고가 100개인데 200개 신청하면 어떻게 될까?
        assertThatThrownBy(() -> product.purchased(200L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("티셔츠의 상품 재고가 부족합니다.");
    }
}
