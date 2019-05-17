package com.minipoly.android.entity;

import com.minipoly.android.ProgressInfo;

public class Response<T> {
    private T data;
    private ProgressInfo info;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ProgressInfo getInfo() {
        return info;
    }

    public void setInfo(ProgressInfo info) {
        this.info = info;
    }
}
