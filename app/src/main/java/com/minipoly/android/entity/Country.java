package com.minipoly.android.entity;

public class Country extends Pin {
    private String id;
    private int followers;
    private int dealsCount;
    private int realestateCount;
    private float dealsValue;
    private float realestateValue;
    private String name;
    private String nameAr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getDealsCount() {
        return dealsCount;
    }

    public void setDealsCount(int dealsCount) {
        this.dealsCount = dealsCount;
    }

    public int getRealestateCount() {
        return realestateCount;
    }

    public void setRealestateCount(int realestateCount) {
        this.realestateCount = realestateCount;
    }

    public float getDealsValue() {
        return dealsValue;
    }

    public void setDealsValue(float dealsValue) {
        this.dealsValue = dealsValue;
    }

    public float getRealestateValue() {
        return realestateValue;
    }

    public void setRealestateValue(float realestateValue) {
        this.realestateValue = realestateValue;
    }
}
