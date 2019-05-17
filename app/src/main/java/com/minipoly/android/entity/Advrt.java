package com.minipoly.android.entity;

public class Advrt {
    private String id;
    private boolean realEstate;
    private float price;
    private String categoryId;
    private String subcategoryId;
    private String [] images;
    private String [] videos; 
    private String title;
    private String text;
    private String userId;
    private Pin locInfo;

    public Pin getLocInfo() {
        return locInfo;
    }

    public void setLocInfo(Pin locInfo) {
        this.locInfo = locInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRealEstate() {
        return realEstate;
    }

    public void setRealEstate(boolean realEstate) {
        this.realEstate = realEstate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
