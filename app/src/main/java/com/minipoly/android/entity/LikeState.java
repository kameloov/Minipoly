package com.minipoly.android.entity;

import com.minipoly.android.num.ItemType;

public class LikeState {
    private boolean liked;
    private boolean disliked;
    private String id;
    private ItemType type;


    public LikeState(String id, ItemType type) {
        this.id = id;
        this.type = type;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isDisliked() {
        return disliked;
    }

    public void setDisliked(boolean disliked) {
        this.disliked = disliked;
    }

    public void like() {

    }

}
