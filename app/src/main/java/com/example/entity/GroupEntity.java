package com.example.entity;

public class GroupEntity {
    private String name;
    private boolean isCheck=false;

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

    public GroupEntity(String name) {
        this.name = name;
    }
}
