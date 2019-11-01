package com.minipoly.android.filters;


import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.minipoly.android.entity.CarInfo;
import com.minipoly.android.entity.ValueFilter;

import java.io.Serializable;
import java.util.List;

public class CarFilter extends AdvrtFilter implements Serializable {

    private CarInfo min = new CarInfo();
    private CarInfo max = new CarInfo();
    private boolean transmissionStatus;

    @Bindable
    public boolean isTransmissionStatus() {
        return transmissionStatus;
    }

    public void setTransmissionStatus(boolean transmissionStatus) {
        this.transmissionStatus = transmissionStatus;
        notifyPropertyChanged(BR.TransmissionStatus);
    }

    public String getColor() {
        return min.getColor();
    }

    public void setColor(String color) {
        min.setColor(color);
        max.setColor(color);
    }

    public String getFuelType() {
        return min.getFuel();
    }

    public void setFuelType(String type) {
        min.setFuel(type);
        max.setFuel(type);
    }

    public boolean isAutomatic() {
        return min.isAutomatic();
    }

    public void setAutomatic(boolean automatic) {
        min.setAutomatic(automatic);
        max.setAutomatic(automatic);
    }

    public void setBrnd(String brand) {
        min.setBrand(brand);
        max.setBrand(brand);
    }

    public String getBrand() {
        return min.getBrand();
    }

    public String getModel() {
        return min.getType();
    }

    public void setModel(String model) {
        min.setType(model);
        max.setType(model);
    }

    public int getKilometerMin() {
        return min.getKilometres();
    }

    public void setKilometerMin(int value) {
        min.setKilometres(value);
    }

    public int getKilometerMax() {
        return max.getKilometres();
    }

    public void setKilometerMax(int value) {
        max.setKilometres(value);
    }

    @Override
    public List<ValueFilter> getFilters() {
        List<ValueFilter> filters = super.getFilters();
        if (getColor() != null)
            filters.add(new ValueFilter("color", ValueFilter.FilterType.EQUAL, getColor()));
        if (getBrand() != null)
            filters.add(new ValueFilter("brand", ValueFilter.FilterType.EQUAL, getBrand()));
        if (getModel() != null)
            filters.add(new ValueFilter("type", ValueFilter.FilterType.EQUAL, getModel()));
        if (getFuelType() != null)
            filters.add(new ValueFilter("fuel", ValueFilter.FilterType.EQUAL, getFuelType()));
        if (transmissionStatus)
            filters.add(new ValueFilter("automatic", ValueFilter.FilterType.EQUAL, isAutomatic()));
        return filters;
    }
}
