package com.minipoly.android.entity;

import java.util.ArrayList;
import java.util.List;

public class Interaction {
    private List<String> like;
    private List<String> dislike;
    private List<String> comment;

    public Interaction() {
        like = new ArrayList<>();
        dislike = new ArrayList<>();
        comment = new ArrayList<>();
    }

    public List<String> getLike() {
        return like;
    }

    public void setLike(List<String> like) {
        this.like = like;
    }

    public List<String> getDislike() {
        return dislike;
    }

    public void setDislike(List<String> dislike) {
        this.dislike = dislike;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }
}
