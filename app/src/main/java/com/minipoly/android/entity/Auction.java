package com.minipoly.android.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Auction extends Realestate implements Serializable {

    private String id;
    private Bid lastBid;
    @ServerTimestamp
    private Date startTime;
    private Date endTime;
    private boolean active;
    private List<String> blocked;

    public Auction() {

    }

    public Auction(boolean active) {
        this.active = active;
    }

    public List<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(List<String> blocked) {
        this.blocked = blocked;
    }

    public String getId() {
        return id;
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
