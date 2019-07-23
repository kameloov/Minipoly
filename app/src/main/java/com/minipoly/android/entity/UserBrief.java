package com.minipoly.android.entity;

import java.io.Serializable;

public class UserBrief implements Serializable {
    private String id;
    private String name;
    private int stars;
    private String picture;
    private int deals;
    private String token;
    private String phone;

    public UserBrief(User user) {
        id = user.getId();
        name = user.getName();
        stars = user.getStars();
        picture = user.getPicture();
        deals = user.getDealCount();
        token = user.getToken();
        phone = user.getPhone();
    }

    public UserBrief() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getDeals() {
        return deals;
    }

    public void setDeals(int deals) {
        this.deals = deals;
    }
}
