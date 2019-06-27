package com.minipoly.android.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.minipoly.android.BR;

public class CustomRadio extends BaseObservable {
    private boolean checked;
    private String name1;
    private String name2;
    private int resourceId;

    public CustomRadio(boolean checked, String name1, String name2) {
        this.checked = checked;
        this.name1 = name1;
        this.name2 = name2;
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Bindable
    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
        notifyPropertyChanged(BR.name1);
    }

    @Bindable
    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
        notifyPropertyChanged(BR.name2);
    }
}
