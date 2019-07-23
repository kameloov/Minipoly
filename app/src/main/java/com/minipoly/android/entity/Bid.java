package com.minipoly.android.entity;

import java.io.Serializable;

public class Bid implements Serializable {
    private String id;
    private int value;
    private UserBrief bidder;
    private String advrtId;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
