package com.minipoly.android.filters;


import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.minipoly.android.entity.ValueFilter;

import java.io.Serializable;
import java.util.List;

public class RealestateFilter extends AdvrtFilter implements Serializable {
    private int rooms;
    private int bathrooms;
    private boolean furnished;
    private boolean monthly;
    private boolean yearly;
    private boolean rent;
    private String age = "";
    private boolean rentStatus;
    private boolean durationStatus;
    private boolean furnishedStatus;


    @Bindable
    public boolean isRentStatus() {
        return rentStatus;
    }

    public RealestateFilter setRentStatus(boolean rentStatus) {
        this.rentStatus = rentStatus;
        notifyPropertyChanged(BR.rentStatus);
        return this;
    }

    @Bindable
    public boolean isDurationStatus() {
        return durationStatus;
    }

    public RealestateFilter setDurationStatus(boolean value) {
        this.durationStatus = value;
        notifyPropertyChanged(BR.durationStatus);
        return this;
    }

    @Bindable
    public boolean isFurnishedStatus() {
        return furnishedStatus;
    }

    public RealestateFilter setFurnishedStatus(boolean value) {
        this.furnishedStatus = value;
        notifyPropertyChanged(BR.furnishedStatus);
        return this;
    }

    @Bindable
    public int getRooms() {
        return rooms;
    }

    public RealestateFilter setRooms(int rooms) {
        this.rooms = rooms < 0 ? 0 : rooms;
        notifyPropertyChanged(BR.rooms);
        return this;
    }

    @Bindable
    public int getBathrooms() {
        return bathrooms;
    }

    public RealestateFilter setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms < 0 ? 0 : bathrooms;
        notifyPropertyChanged(BR.bathrooms);
        return this;
    }

    @Bindable
    public boolean isFurnished() {
        return furnished;
    }

    public RealestateFilter setFurnished(boolean furnished) {
        this.furnished = furnished;
        notifyPropertyChanged(BR.furnished);
        return this;
    }

    @Bindable
    public boolean isMonthly() {
        return monthly;
    }

    public RealestateFilter setMonthly(boolean monthly) {
        this.monthly = monthly;
        notifyPropertyChanged(BR.monthly);
        return this;
    }

    @Bindable
    public boolean isYearly() {
        return yearly;
    }

    public RealestateFilter setYearly(boolean yearly) {
        this.yearly = yearly;
        notifyPropertyChanged(BR.yearly);
        return this;
    }

    @Bindable
    public boolean isRent() {
        return rent;
    }

    public RealestateFilter setRent(boolean rent) {
        this.rent = rent;
        notifyPropertyChanged(BR.rent);
        return this;
    }

    @Bindable
    public String getAge() {
        return age;
    }


    public RealestateFilter setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
        return this;
    }

    @Override
    public List<ValueFilter> getFilters() {
        List<ValueFilter> filters = super.getFilters();
        if (rooms > 0)
            filters.add(new ValueFilter("roomCount", ValueFilter.FilterType.EQUAL, rooms));
        if (bathrooms > 0)
            filters.add(new ValueFilter("bathroomCount", ValueFilter.FilterType.EQUAL, rooms));
        if (rentStatus)
            filters.add(new ValueFilter("isRent", ValueFilter.FilterType.EQUAL, rent));
        if (durationStatus) {
            filters.add(new ValueFilter("monthlyRent", ValueFilter.FilterType.EQUAL, monthly));
            filters.add(new ValueFilter("yearlyRent", ValueFilter.FilterType.EQUAL, yearly));
        }
        if (age.length() > 0)
            filters.add(new ValueFilter("old", ValueFilter.FilterType.EQUAL, age));
        if (furnishedStatus)
            filters.add(new ValueFilter("furnished", ValueFilter.FilterType.EQUAL, furnished));

        return filters;
    }
}
