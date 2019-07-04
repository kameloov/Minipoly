package com.minipoly.android.entity;

public class Bid {
    private String id;
    private float value;
    private UserBrief bidder;
    private String advrtId;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public UserBrief getBidder() {
        return bidder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBidder(UserBrief bidder) {
        this.bidder = bidder;
    }

    public String getAdvrtId() {
        return advrtId;
    }

    public void setAdvrtId(String advrtId) {
        this.advrtId = advrtId;
    }
}
