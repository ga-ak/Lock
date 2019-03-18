package com.kim.cheolho.lock.dto;

import java.util.ArrayList;

public class ManageKeyListDTO {
    ArrayList<ManageKeyDTO> manageKeyDTOS;

    public ManageKeyListDTO(ArrayList<ManageKeyDTO> manageKeyDTOS) {
        this.manageKeyDTOS = manageKeyDTOS;
    }

    public ArrayList<ManageKeyDTO> getManageKeyDTOS() {
        return manageKeyDTOS;
    }
}
