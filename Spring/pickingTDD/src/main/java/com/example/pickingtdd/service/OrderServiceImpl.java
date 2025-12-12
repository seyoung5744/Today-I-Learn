package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.OrderDetail;
import com.example.pickingtdd.entity.OrderStateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderDetailService orderDetailService;

    @Override
    public Order makeOrder(Order order) throws Exception {
        if (orderMakeValidation(order)) {
            for (OrderDetail orderDetail : order.getOrderDetailList()) {
                try {
                    orderDetailService.makeOrderDetail(orderDetail);
                } catch (Exception e) {
                    throw e;
                }
            }
            return order;
        }
        throw new Exception("order validation fail");
    }

    @Override
    public void changeOrderState(Order order, OrderStateEnum orderStateEnum) {
        order.setState(orderStateEnum);
    }

    private boolean orderMakeValidation(Order order) {
        if (order.getOrderId() == null) {
            return false;
        }

        if (order.getState() == null) {
            return false;
        }

        if (order.getOrderDetailList() == null || order.getOrderDetailList().size() < 1) {
            return false;
        }
        return true;
    }
}
