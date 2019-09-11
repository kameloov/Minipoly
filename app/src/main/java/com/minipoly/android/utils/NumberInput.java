package com.minipoly.android.utils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class NumberInput extends BaseObservable {

    private int value = 0;

    @Bindable
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    public void increase() {
        this.value += 1;
        notifyPropertyChanged(BR.value);
    }

    public void decrease() {
        this.value -= 1;
        notifyPropertyChanged(BR.value);
    }
}
