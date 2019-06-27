package com.minipoly.android.entity;

import java.io.Serializable;

public class ComputerInfo extends Market implements Serializable {
    private int ram;
    private float screenSize;
    private String processor;

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public float getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(float screenSize) {
        this.screenSize = screenSize;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }
}
