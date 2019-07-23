package com.minipoly.android.entity;

public class Notification {
    private String id;
    private String text;
    private NotificationType type;
    private String senderId;
    private String enderName;
    private String itemId;
    private String itemTitle;

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

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getEnderName() {
        return enderName;
    }

    public void setEnderName(String enderName) {
        this.enderName = enderName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    enum NotificationType {AUCTION, Advrt}
}
