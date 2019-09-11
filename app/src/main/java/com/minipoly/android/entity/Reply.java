package com.minipoly.android.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Reply {
    private String id ;
    private UserBrief userBrief;
    private String commentId;
    private String text;
    private int like;
    private int dislike;
    @ServerTimestamp
    private Date date;


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

    public UserBrief getUserBrief() {
        return userBrief;
    }

    public void setUserBrief(UserBrief userBrief) {
        this.userBrief = userBrief;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
