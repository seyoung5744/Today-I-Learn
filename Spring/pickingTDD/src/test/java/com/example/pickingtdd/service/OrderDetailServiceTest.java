package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.OrderDetail;
import com.example.pickingtdd.entity.Sku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class OrderDetailServiceTest {

    @Autowired
    OrderDetailService orderDetailService;

    OrderDetail orderDetailSuccess;
    OrderDetail orderDetailFail;

    @BeforeEach
    void setUp() {
        orderDetailSuccess = new OrderDetail();
        orderDetailSuccess.setOrderDetailId(1L);
        orderDetailSuccess.setOrderId(1L);
        orderDetailSuccess.setSku(new Sku());
        orderDetailSuccess.setAmount(10);

        orderDetailFail = new OrderDetail();
        orderDetailFail.setOrderDetailId(2L);
    }

    @Test
    void orderDetailMake_success() {
        OrderDetail orderDetail = new OrderDetail();

        try {
            orderDetail = orderDetailService.makeOrderDetail(orderDetailSuccess);
        } catch (Exception e) {
            // doSomethine
        }

        assertThat(orderDetail.getOrderDetailId()).isEqualTo(1L);
        assertThat(orderDetail.getOrderId()).isEqualTo(1L);
        assertThat(orderDetail.getAmount()).isEqualTo(10);
    }

    @Test
    void orderDetailValidation_success() {
        OrderDetail orderDetail = new OrderDetail();
        try {
            orderDetail = orderDetailService.makeOrderDetail(orderDetailSuccess);
        } catch (Exception e) {
            fail("should not throw exception");
        }

        assertThat(orderDetail.getOrderDetailId()).isEqualTo(1L);
    }

    @Test
    void orderDetailValidation_fail() {
        assertThatThrownBy(() -> orderDetailService.makeOrderDetail(orderDetailFail))
                .isInstanceOf(Exception.class)
                .hasMessage("orderDetail validation fail");
    }
}
