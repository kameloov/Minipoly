package com.minipoly.android.entity;

import java.util.List;

public class Car extends FilterObject {
    private List<String> models;

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }
}
