package com.minipoly.android.entity;

import java.io.Serializable;

public class MobileInfo implements Serializable {
    private int ram;
    private float screen;
    private String color;
    private String category;
    private int battery;
    private int storage;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

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
