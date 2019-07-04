package com.minipoly.android.entity;

import java.io.Serializable;
import java.util.Date;

public class Auction extends Realestate implements Serializable {

    private String id;
    private Bid lastBid;
    private Date startTime;
    private Date endTime;
    private boolean active;
    private float startPrice;

    public Auction(boolean active) {
        this.active = active;
    }


    public String getId() {
        return id;
    }

    public float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(float startPrice) {
        this.startPrice = startPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bid getLastBid() {
        return lastBid;
    }

    public void setLastBid(Bid lastBid) {
        this.lastBid = lastBid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
