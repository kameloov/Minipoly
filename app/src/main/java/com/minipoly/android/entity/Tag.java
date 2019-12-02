package com.minipoly.android.entity;

import java.util.List;

public class Tag {
    private String id;
    private List<String> names;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
