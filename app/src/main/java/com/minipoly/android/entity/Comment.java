package com.minipoly.android.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comment {
    private String id ;
    private String text;
    private String advrtId;
    private UserBrief userBrief;
    private int like;
    private int dislike;
    private Reply reply;
    private int replyCount;

    @ServerTimestamp
    private Date date;

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdvrtId() {
        return advrtId;
    }

    public void setAdvrtId(String advrtId) {
        this.advrtId = advrtId;
    }

    public UserBrief getUserBrief() {
        return userBrief;
    }

    public void setUserBrief(UserBrief userBrief) {
        this.userBrief = userBrief;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }
}
