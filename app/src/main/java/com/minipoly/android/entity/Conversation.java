package com.minipoly.android.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Conversation implements Serializable {
    private String id;
    private UserBrief user1;
    private UserBrief user2;
    private UserBrief user3;
    private String advrtId;
    private Message lastMsg;
    private List<String> userIds;
    private String advrtTitle;

    public String getAdvrtTitle() {
        return advrtTitle;
    }

    public void setAdvrtTitle(String advrtTitle) {
        this.advrtTitle = advrtTitle;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    @ServerTimestamp
    private Date lastActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserBrief getUser1() {
        return user1;
    }

    public void setUser1(UserBrief user1) {
        this.user1 = user1;
    }

    public UserBrief getUser2() {
        return user2;
    }

    public void setUser2(UserBrief user2) {
        this.user2 = user2;
    }

    public UserBrief getUser3() {
        return user3;
    }

    public void setUser3(UserBrief user3) {
        this.user3 = user3;
    }

    public String getAdvrtId() {
        return advrtId;
    }

    public void setAdvrtId(String advrtId) {
        this.advrtId = advrtId;
    }

    public Message getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(Message lastMsg) {
        this.lastMsg = lastMsg;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }
}
