package com.minipoly.android.entity;

public class Category {
    private String id ;
    private String title ;
    private String titleAr;
    private String icon;
    private int dealsCount;
    private int adsCount;
    private boolean market;

    public int getAdsCount() {
        return adsCount;
    }

    public void setAdsCount(int adsCount) {
        this.adsCount = adsCount;
    }

    public boolean isMarket() {
        return market;
    }

    public void setMarket(boolean market) {
        this.market = market;
    }

    public int getDealsCount() {
        return dealsCount;
    }

    public void setDealsCount(int dealsCount) {
        this.dealsCount = dealsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }
}
