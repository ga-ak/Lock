package com.kim.cheolho.lock.dto;

public class ManageKeyDTO {
    private String keyName;
    private int borrowNum;

    public ManageKeyDTO(String keyName, int borrowNum) {
        this.keyName = keyName;
        this.borrowNum = borrowNum;
    }

    public String getKeyName() {
        return keyName;
    }

    public int getBorrowNum() {
        return borrowNum;
    }
}
