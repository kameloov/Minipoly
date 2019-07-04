package com.minipoly.android.entity;

import java.util.List;

public class MobileMisc {
    private List<String> colors;
    private List<Integer> ram;
    private List<Integer> Storage;
    private List<String> type;
    private List<Integer> battery;

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<Integer> getRam() {
        return ram;
    }

    public void setRam(List<Integer> ram) {
        this.ram = ram;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<Integer> getStorage() {
        return Storage;
    }

    public void setStorage(List<Integer> storage) {
        Storage = storage;
    }


    public List<Integer> getBattery() {
        return battery;
    }

    public void setBattery(List<Integer> battery) {
        this.battery = battery;
    }
}
