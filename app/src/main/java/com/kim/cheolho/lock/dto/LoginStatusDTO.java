package com.kim.cheolho.lock.dto;

import java.util.ArrayList;

public class LoginStatusDTO {
    boolean isLoged;
    ArrayList<DoorLockDTO> doorLockDTOS;

    public LoginStatusDTO(boolean isLoged, ArrayList<DoorLockDTO> myKeyDTOS) {
        this.isLoged = isLoged;
        this.doorLockDTOS = myKeyDTOS;
    }

    public boolean isLoged() {
        return isLoged;
    }

    public ArrayList<DoorLockDTO> getDoorLockDTOS() {
        return doorLockDTOS;
    }
}
