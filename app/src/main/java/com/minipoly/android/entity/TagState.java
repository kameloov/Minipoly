package com.minipoly.android.entity;

public class TagState {
    private boolean enabled;

    public TagState() {
    }

    public TagState(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
