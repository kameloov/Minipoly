package com.minipoly.android.utils;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.entity.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageBuffer {

    private static MutableLiveData<List<Image>> images = new MutableLiveData<>(new ArrayList<>());

    public static void reset() {
        images.postValue(new ArrayList<>());
    }

    public static void addImage(Image image) {
        List<Image> lst = images.getValue();
        lst.add(image);
        images.postValue(lst);
    }

    public static void addImage(Uri selectedImageUri) {
        Image image = new Image();
        image.setUri(selectedImageUri);
        Date date = new Date();
        String id = String.valueOf(date.getTime());
        image.setId(id);
        addImage(image);
    }

    public static void removeImage(Image image) {
        List<Image> lst = images.getValue();
        lst.remove(image);
        images.postValue(lst);
    }

    public static LiveData<List<Image>> getImages() {
        return images;
    }
}
