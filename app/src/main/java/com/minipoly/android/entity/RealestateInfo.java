package com.minipoly.android.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;

public class RealestateInfo extends BaseObservable implements Serializable {
    private boolean isRent;
    private float area;
    private int roomCount;
    private int bathroomCount;
    private boolean furnished;
    private int old;
    private boolean monthlyRent;
    private boolean yearlyRent;

    public boolean isRent() {
        return isRent;
    }

    public void setRent(boolean rent) {
        isRent = rent;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    @Bindable
    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
        notifyPropertyChanged(BR.roomCount);
    }

    @Bindable
    public int getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(int bathroomCount) {
        this.bathroomCount = bathroomCount;
        notifyPropertyChanged(BR.bathroomCount);
    }

    public boolean isFurnished() {
        return furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public boolean isMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(boolean monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public boolean isYearlyRent() {
        return yearlyRent;
    }

    public void setYearlyRent(boolean yearlyRent) {
        this.yearlyRent = yearlyRent;
    }
}
