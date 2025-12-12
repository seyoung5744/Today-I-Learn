package com.example.pickingtdd.service;

import com.example.pickingtdd.entity.Picker;
import com.example.pickingtdd.entity.PickerStateEnum;
import com.example.pickingtdd.entity.PickingList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickerServiceImpl implements PickerService {

    @Override
    public Picker assignPickingList(Picker picker, PickingList pickingList) {
        picker.setAssignedPickingList(pickingList);
        picker.setAssignedOrder(pickingList.getOrder());
        picker.setState(PickerStateEnum.ASSIGNED);
        return picker;
    }
}
