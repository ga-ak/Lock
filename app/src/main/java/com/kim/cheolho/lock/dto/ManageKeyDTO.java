package com.kim.cheolho.lock.dto;

public class ManageKeyDTO {
    private String keyName;
    private int givenNum;

    public ManageKeyDTO(String keyName, int givenNum) {
        this.keyName = keyName;
        this.givenNum = givenNum;
    }

    public String getKeyName() {
        return keyName;
    }

    public int getGivenNum() {
        return givenNum;
    }
}
