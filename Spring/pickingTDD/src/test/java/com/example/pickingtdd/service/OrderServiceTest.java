package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.OrderDetail;
import com.example.pickingtdd.entity.OrderStateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderDetailService orderDetailService;

    @InjectMocks
    OrderServiceImpl orderService;

    Order orderSuccess;
    Order orderFail;

    @BeforeEach
    void setUp() {
        OrderDetail orderDetail = new OrderDetail();
        orderSuccess = new Order();
        orderSuccess.setOrderId(1L);
        orderSuccess.setState(OrderStateEnum.ORDERED);
        orderSuccess.setOrderDetailList(List.of(orderDetail));

        orderFail = new Order();
        orderFail.setOrderId(null);
        orderFail.setState(null);
    }

    @Test
    void orderMake_success() {
        Order order = new Order();

        try {
            order = orderService.makeOrder(orderSuccess);
        } catch (Exception e) {
            // do nothing
        }

        assertThat(order.getOrderId()).isEqualTo(1L);
        assertThat(order.getState()).isEqualTo(OrderStateEnum.ORDERED);
    }

    @Test
    void orderValidation_success() {
        Order order = new Order();

        try {
            order = orderService.makeOrder(orderSuccess);
        } catch (Exception e) {
            fail("should not throw exception");
        }

        assertThat(order.getOrderId()).isEqualTo(1L);
        assertThat(order.getState()).isEqualTo(OrderStateEnum.ORDERED);
    }

    @Test
    void orderValidation_fail() {
        assertThatThrownBy(() -> orderService.makeOrder(orderFail))
                .isInstanceOf(Exception.class)
                .hasMessage("order validation fail");
    }

    @Test
    void changeOrderState() {
        orderService.changeOrderState(orderSuccess, OrderStateEnum.LIST_MADE);

        assertThat(orderSuccess.getState()).isEqualTo(OrderStateEnum.LIST_MADE);
    }
}
