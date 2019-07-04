package com.minipoly.android.entity;

import java.util.List;

public class ComputerMisc {
    private List<Float> screen;
    private List<String> processor;
    private List<String> graphic;
    private List<Integer> ram;
    private List<Integer> storage;

    public List<Float> getScreen() {
        return screen;
    }

    public void setScreen(List<Float> screen) {
        this.screen = screen;
    }

    public List<String> getProcessor() {
        return processor;
    }

    public void setProcessor(List<String> processor) {
        this.processor = processor;
    }

    public List<String> getGraphic() {
        return graphic;
    }

    public void setGraphic(List<String> graphic) {
        this.graphic = graphic;
    }

    public List<Integer> getRam() {
        return ram;
    }

    public void setRam(List<Integer> ram) {
        this.ram = ram;
    }

    public List<Integer> getStorage() {
        return storage;
    }

    public void setStorage(List<Integer> storage) {
        this.storage = storage;
    }
}
