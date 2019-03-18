package com.kim.cheolho.lock.dto;

import com.kim.cheolho.lock.dto.ManageUserDTO;

import java.util.ArrayList;

public class ManageUserListDTO {
    ArrayList<ManageUserDTO> manageUserDTOS;

    public ManageUserListDTO(ArrayList<ManageUserDTO> manageUserDTOS) {
        this.manageUserDTOS = manageUserDTOS;
    }

    public ArrayList<ManageUserDTO> getManageUserDTOS() {
        return manageUserDTOS;
    }
}