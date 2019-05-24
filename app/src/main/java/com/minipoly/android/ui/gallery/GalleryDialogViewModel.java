package com.minipoly.android.ui.gallery;

import android.net.Uri;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.adapter.ImageAdapter;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.TransferInfo;
import com.minipoly.android.utils.Uploader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryDialogViewModel extends ViewModel {
    private ImageAdapter adapter;
    private MediatorLiveData<TransferInfo> info = new MediatorLiveData<>();
    private Uploader uploader;

    public ImageAdapter getAdapter() {
        return adapter;
    }
    public LiveData<TransferInfo> getInfo() {
        return info;
    }

    public Uploader getUploader() {
        return uploader;
    }

    public void setUploader(Uploader uploader) {
        this.uploader = uploader;
    }

    public void Upload() {
        List<Image> images = new ArrayList<>();
        images.addAll(adapter.getImages());
        images.remove(0);
        info.addSource(uploader.upload(images), transferInfo -> info.setValue(transferInfo));
    }


    public void initAdapter(View.OnClickListener o) {
        this.adapter = new ImageAdapter(o);
        if (uploader.getImages() != null)
            adapter.setImages(uploader.getImages());
    }

    public void addImage(Uri selectedImageUri) {
        if (adapter != null) {
            Image image = new Image();
            image.setUri(selectedImageUri);
            Date date = new Date();
            String id = String.valueOf(date.getTime());
            image.setId(id);
            adapter.addImage(image);
        }
    }
}
