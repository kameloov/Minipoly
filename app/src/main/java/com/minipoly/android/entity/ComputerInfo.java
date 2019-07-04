package com.minipoly.android.entity;

import java.io.Serializable;

public class ComputerInfo extends Market implements Serializable {
    private int ram;
    private float screenSize;
    private String processor;
    private int storage;
    private boolean ssd;
    private int graphic;

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public boolean isSsd() {
        return ssd;
    }

    public void setSsd(boolean ssd) {
        this.ssd = ssd;
    }

    public int getGraphic() {
        return graphic;
    }

    public void setGraphic(int graphic) {
        this.graphic = graphic;
    }

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
