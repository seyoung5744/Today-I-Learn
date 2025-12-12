package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.*;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PickingListServiceTest {

    @Mock
    OrderService orderService;

    @Spy
    PickerService pickerService = new PickerServiceImpl();

    @InjectMocks
    PickingListServiceImpl pickingListService;

    Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrderId(1L);
        order.setState(OrderStateEnum.ORDERED);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(1L);
        orderDetail.setAmount(10);
        orderDetail.setOrderDetailId(1L);
        orderDetail.setSku(new Sku());

        order.setOrderDetailList(List.of(orderDetail));
    }

    @Test
    void makePickingList() {
        PickingList assertPickingList = new PickingList();
        assertPickingList.setOrder(order);
        assertPickingList.setSkuAmountMap(
                Maps.newHashMap(
                        order.getOrderDetailList().get(0).getSku(),
                        order.getOrderDetailList().get(0).getAmount())
        );
        assertPickingList.setState(PickingStateEnum.NOT_ASSIGNED);
        assertPickingList.setPicker(null);

        PickingList pickingList = pickingListService.makePickingList(order);

        assertThat(pickingList.getOrder()).isEqualTo(assertPickingList.getOrder());
        assertThat(pickingList.getState()).isEqualTo(assertPickingList.getState());
        assertThat(pickingList.getSkuAmountMap().get(order.getOrderDetailList().get(0).getSku()))
                .isEqualTo(order.getOrderDetailList().get(0).getAmount());
    }

    @Test
    void assignPicker() {
        PickingList pickingList = pickingListService.makePickingList(order);

        Picker picker = new Picker();

        PickingList assignedPickingList = pickingListService.assignPicker(pickingList, picker);

        assertThat(assignedPickingList.getPicker()).isEqualTo(picker);
        assertThat(assignedPickingList.getState()).isEqualTo(PickingStateEnum.ASSIGNED);
        assertThat(assignedPickingList.getPicker().getAssignedPickingList()).isNotNull();
    }
}
