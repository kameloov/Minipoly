package com.minipoly.android.entity;

import java.io.Serializable;

public class Market extends Pin implements Serializable {
    private int old;
    private boolean used;

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}