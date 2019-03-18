package com.kim.cheolho.lock.dto;

import java.util.ArrayList;

public class MyKeyListDTO {
    private ArrayList<DoorLockDTO> doorLockDTOS;

    public MyKeyListDTO(ArrayList<DoorLockDTO> doorLockDTOArrayList) {
        this.doorLockDTOS = doorLockDTOArrayList;
    }

    public ArrayList<DoorLockDTO> getDoorLockDTOArrayList() {
        return doorLockDTOS;
    }
}
