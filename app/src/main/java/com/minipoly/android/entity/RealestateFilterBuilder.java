package com.minipoly.android.entity;

import java.util.ArrayList;
import java.util.List;

public class RealestateFilterBuilder {
    private RealestateParmas params = new RealestateParmas();


    public RealestateFilterBuilder setOrderType(OrderType orderType) {
        params.orderType = orderType;
        return this;
    }

    public RealestateFilterBuilder setRoomCount(int value) {
        params.rooms = value;
        return this;
    }

    public RealestateFilterBuilder setBathroomCount(int value) {
        params.bathrooms = value;
        return this;
    }

    public RealestateFilterBuilder setMonthly(boolean value) {
        params.monthly = value ? 1 : 0;
        return this;
    }

    public RealestateFilterBuilder setYearly(boolean value) {
        params.yearly = value ? 1 : 0;
        return this;
    }

    public RealestateFilterBuilder setFurnished(boolean value) {
        params.funished = value ? 1 : 0;
        return this;
    }

    public RealestateParmas build() {
        return params;
    }

    public List<ValueFilter> getFilters() {
        ArrayList<ValueFilter> filters = new ArrayList<>();
        if (params.rooms != 0)
            filters.add(new ValueFilter("realestateInfo.roomCount", ValueFilter.FilterType.EQUAL, params.rooms));
        if (params.rooms != 0)
            filters.add(new ValueFilter("realestateInfo.bathroomCount", ValueFilter.FilterType.EQUAL, params.bathrooms));
        if (params.monthly != -1)
            filters.add(new ValueFilter("realestateInfo.monthlyRent", ValueFilter.FilterType.EQUAL, params.monthly == 1));
        if (params.yearly != -1)
            filters.add(new ValueFilter("realestateInfo.yearlyRent", ValueFilter.FilterType.EQUAL, params.yearly == 1));
        if (params.funished != -1)
            filters.add(new ValueFilter("realestateInfo.furnished", ValueFilter.FilterType.EQUAL, params.funished == 1));
        return filters;
    }

    public enum OrderType {DATE_ASC, DATE_DESC, PRICE_ASC, PRICE_DESC}

    public class RealestateParmas {
        OrderType orderType;
        int rooms = 0;
        int bathrooms = 0;
        int monthly = -1;
        int yearly = -1;
        int funished = -1;

        public OrderType getOrderType() {
            return orderType;
        }

        public void setOrderType(OrderType orderType) {
            this.orderType = orderType;
        }

        public int getRooms() {
            return rooms;
        }

        public void setRooms(int rooms) {
            this.rooms = rooms;
        }

        public int getBathrooms() {
            return bathrooms;
        }

        public void setBathrooms(int bathrooms) {
            this.bathrooms = bathrooms;
        }

        public int getMonthly() {
            return monthly;
        }

        public void setMonthly(int monthly) {
            this.monthly = monthly;
        }

        public int getYearly() {
            return yearly;
        }

        public void setYearly(int yearly) {
            this.yearly = yearly;
        }

        public int getFunished() {
            return funished;
        }

        public void setFunished(int funished) {
            this.funished = funished;
        }
    }
}
