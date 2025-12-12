package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Order;
import com.example.pickingtdd.entity.Picker;
import com.example.pickingtdd.entity.PickerStateEnum;
import com.example.pickingtdd.entity.PickingList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PickerServiceTest {

    @Autowired
    PickerService pickerService;

    @Test
    void assignPicker() {
        Picker picker = new Picker();
        picker.setPickerId(1L);
        picker.setState(PickerStateEnum.REST);

        PickingList pickingList = new PickingList();
        pickingList.setOrder(new Order());
        picker = pickerService.assignPickingList(picker, pickingList);

        assertThat(picker.getState()).isEqualTo(PickerStateEnum.ASSIGNED);
        assertThat(picker.getAssignedOrder()).isNotNull();
        assertThat(picker.getAssignedPickingList()).isNotNull();
    }
}
