package com.minipoly.android.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Notification {
    private String id;
    private String text;
    private int type;
    private String itemId;
    private String link;
    private UserBrief user;
    private boolean seen;
    @ServerTimestamp
    private Date timeStamp;
    private int actionType; /// 1 like , 2 dislike ,3 comment , 4  bid ,5 notify

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public UserBrief getUser() {
        return user;
    }

    public void setUser(UserBrief user) {
        this.user = user;
    }

}
