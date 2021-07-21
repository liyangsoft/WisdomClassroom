package com.example.entity;

public class ColorEntity {
    private boolean flag=false;
    private String color;

    public ColorEntity( String color) {
        this.color = color;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
