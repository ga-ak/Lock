package com.kim.cheolho.lock.dto;

public class ManageUserDTO {
    private String user_user_id;
    private int access_number;
    private String expire_date;

    public ManageUserDTO(String user_user_id, int access_number, String expire_date) {
        this.user_user_id = user_user_id;
        this.access_number = access_number;
        this.expire_date = expire_date;
    }

    public String getUser_user_id() {
        return user_user_id;
    }

    public int getAccess_number() {
        return access_number;
    }

    public String getExpire_date() {
        return expire_date;
    }
}


