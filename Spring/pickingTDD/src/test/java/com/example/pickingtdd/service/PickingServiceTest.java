package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.*;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
public class PickingServiceTest {

    @Autowired
    PickingService pickingService;

    Order order;
    OrderDetail orderDetail1;
    OrderDetail orderDetail2;
    PickingList pickingList;
    Picker picker;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrderId(1L);
        order.setState(OrderStateEnum.ASSIGNED);

        orderDetail1 = new OrderDetail();
        orderDetail1.setOrderDetailId(1L);
        orderDetail1.setOrderId(1L);
        orderDetail1.setSku(new Sku());
        orderDetail1.setAmount(10);

        orderDetail2 = new OrderDetail();
        orderDetail2.setOrderDetailId(2L);
        orderDetail2.setOrderId(1L);
        orderDetail2.setSku(new Sku());
        orderDetail2.setAmount(10);

        order.setOrderDetailList(List.of(orderDetail1, orderDetail2));

        pickingList = new PickingList();
        pickingList.setOrder(order);
        pickingList.setState(PickingStateEnum.ASSIGNED);
        pickingList.setId(1L);
        pickingList.setSkuAmountMap(Maps.newHashMap(orderDetail1.getSku(), orderDetail1.getAmount()));
        pickingList.getSkuAmountMap().put(orderDetail2.getSku(), orderDetail2.getAmount());
        pickingList.setPickedMap(Maps.newHashMap(orderDetail1.getSku(), 0));
        pickingList.getPickedMap().put(orderDetail2.getSku(), 0);

        picker = new Picker();
        picker.setPickerId(1L);
        picker.setAssignedOrder(order);
        picker.setAssignedPickingList(pickingList);
        picker.setState(PickerStateEnum.ASSIGNED);

        pickingList.setPicker(picker);
    }

    @Test
    void makePicking_one_success() throws Exception {

        try {
            pickingService.pick(pickingList, orderDetail1.getSku());
        } catch (Exception e) {
            fail("should not exception");
        }

        assertThat(pickingList.getState()).isEqualTo(PickingStateEnum.PROGRESS);
        assertThat(picker.getState()).isEqualTo(PickerStateEnum.PROCESS);
        assertThat(pickingList.getPickedMap().get(orderDetail1.getSku())).isEqualTo(1);
    }

    @Test
    void makePicking_DONE_success() {

        try {
            for (int i = 0; i < 10; i++) {
                pickingService.pick(pickingList, orderDetail1.getSku());
                pickingService.pick(pickingList, orderDetail2.getSku());
            }
        } catch (Exception e) {
            fail("should not exception");
        }

        assertThat(pickingList.getState()).isEqualTo(PickingStateEnum.DONE);
        assertThat(pickingList.getPicker().getState()).isEqualTo(PickerStateEnum.DONE);
    }

    @Test
    void makePicking_wrongSku() {
        try {
            pickingService.pick(pickingList, orderDetail1.getSku());
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("wrong sku");
        }
    }

    @Test
    void makePicking_toMuch() {

        try {
            for (int i = 0; i < 12; i++) {
                pickingService.pick(pickingList, orderDetail2.getSku());
            }
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("to much sku");
        }
    }
}
