package com.minipoly.android.entity;

import com.minipoly.android.num.ToeType;

public class Toe {
    private String text;
    private int icon;
    private ToeType toeType;

    public Toe(String text, int icon, ToeType toeType) {
        this.text = text;
        this.icon = icon;
        this.toeType = toeType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ToeType getToeType() {
        return toeType;
    }

    public void setToeType(ToeType toeType) {
        this.toeType = toeType;
    }
}
