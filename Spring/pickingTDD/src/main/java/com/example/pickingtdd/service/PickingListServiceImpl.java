package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PickingListServiceImpl implements PickingListService {

    private final OrderService orderService;
    private final PickerService pickerService;

    @Override
    public PickingList makePickingList(Order order) {
        PickingList pickingList = new PickingList();
        pickingList.setOrder(order);
        pickingList.setState(PickingStateEnum.NOT_ASSIGNED);

        Map<Sku, Integer> skuAmountMap = new HashMap<>();
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            skuAmountMap.put(orderDetail.getSku(), orderDetail.getAmount());
        }
        pickingList.setSkuAmountMap(skuAmountMap);

        orderService.changeOrderState(order, OrderStateEnum.LIST_MADE);
        return pickingList;
    }

    @Override
    public PickingList assignPicker(PickingList pickingList, Picker picker) {
        pickingList.setPicker(picker);
        pickingList.setState(PickingStateEnum.ASSIGNED);

        orderService.changeOrderState(pickingList.getOrder(), OrderStateEnum.ASSIGNED);

        if (picker.getAssignedPickingList() == null || !picker.getAssignedPickingList().equals(pickingList)) {
            pickerService.assignPickingList(picker, pickingList);
        }
        return pickingList;
    }
}
