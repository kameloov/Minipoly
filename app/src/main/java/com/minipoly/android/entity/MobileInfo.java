package com.minipoly.android.entity;

import java.io.Serializable;

public class MobileInfo extends Market implements Serializable {
    private int ram;
    private float screen;
    private String color;


    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public float getScreen() {
        return screen;
    }

    public void setScreen(float screen) {
        this.screen = screen;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
