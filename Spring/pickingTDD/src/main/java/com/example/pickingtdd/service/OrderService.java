package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.OrderStateEnum;

public interface OrderService {

    Order makeOrder(Order order) throws Exception;

    void changeOrderState(Order order, OrderStateEnum orderStateEnum);

}
