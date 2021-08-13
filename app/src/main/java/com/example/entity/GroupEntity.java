package com.example.entity;

public class GroupEntity {
    private String name;
    private String ip;
    private boolean isCheck=false;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public GroupEntity(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }
}
