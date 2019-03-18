package com.kim.cheolho.lock.dto;

public class DoorLockDTO {
    private String keyName;
    private String keyManager;
    private String keyRemain;
    private boolean isTime;
    private int permission;

    public DoorLockDTO(String keyName, String keyManager, String keyRemain, boolean isTime, int permission) {
        this.keyName = keyName;
        this.keyManager = keyManager;
        this.keyRemain = keyRemain;
        this.isTime = isTime;
        this.permission = permission;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getKeyManager() {
        return keyManager;
    }

    public String getKeyRemain() {
        return keyRemain;
    }

    public boolean isTime() {
        return isTime;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
