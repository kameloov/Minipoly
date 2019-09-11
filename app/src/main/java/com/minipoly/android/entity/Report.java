package com.minipoly.android.entity;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable {
    private String id;
    private UserBrief reporter;
    private UserBrief reported;
    private String text;
    @ServerTimestamp
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserBrief getReporter() {
        return reporter;
    }

    public void setReporter(UserBrief reporter) {
        this.reporter = reporter;
    }

    public UserBrief getReported() {
        return reported;
    }

    public void setReported(UserBrief reported) {
        this.reported = reported;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
