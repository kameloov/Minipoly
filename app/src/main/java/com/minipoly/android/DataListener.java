package com.minipoly.android;

public interface DataListener<T> {
    void onComplete(boolean success, T data);
}
