package com.minipoly.android.entity;

import java.util.List;

public class NotificationGroup {
    private String id;
    private List<Notification> notifications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
