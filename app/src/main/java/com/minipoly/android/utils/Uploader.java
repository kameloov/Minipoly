package com.minipoly.android.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.storage.StorageReference;
import com.minipoly.android.ProgressInfo;
import com.minipoly.android.StorageManager;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.TransferInfo;
import com.minipoly.android.livedata.FireLiveUpload;
import com.minipoly.android.repository.MediaRepository;

import java.util.ArrayList;
import java.util.List;

public class Uploader {
    private FireLiveUpload upload;
    private Image current;
    private List<Image> images = new ArrayList<>();
    private LiveData<TransferInfo> uploadInfo = new MutableLiveData<>();
    private MutableLiveData<Image> defaultImage = new MutableLiveData<>();
    private StorageReference reference;
    private String userId;

    public Uploader(String userId) {
        this.userId = userId;
        reference = StorageManager.getUserRoot();
    }

    public LiveData<Image> getDefaultImage() {
        return defaultImage;
    }

    private void setDefaultImage() {
        for (Image i : images) {
            if (i.isIcon())
                defaultImage.postValue(i);
        }
    }

    public LiveData<TransferInfo> upload(List<Image> imageList) {
        images = imageList;
        upload = new FireLiveUpload();
        setDefaultImage();
        current = getNextItem();
        if (uploadNext()) {
            uploadInfo = Transformations.map(upload, input -> {
                TransferInfo ti = new TransferInfo();
                ti.setStatus(ProgressInfo.PROGRESS);
                switch (input.getStatus()) {
                    case SUCCESS:
                        current.setUploaded(true);
                        MediaRepository.addImage(current);
                        if (getCompleteCount() == imageList.size())
                            ti.setStatus(ProgressInfo.SUCCESS);
                        else {
                            ti.setStatus(ProgressInfo.PROGRESS);
                            uploadNext();
                        }
                        break;
                    case FAIL:
                        uploadNext();
                        ti.setStatus(ProgressInfo.PROGRESS);
                        break;
                }
                int complete = getCompleteCount();
                ti.setProgress(input.getProgress());
                ti.setOverallProgress(complete * 100 / imageList.size());
                ti.setText(complete + "/" + imageList.size());
                return ti;
            });
        }
        return uploadInfo;
    }

    private boolean uploadNext() {
        current = getNextItem();
        if (current != null) {
            String id = MediaRepository.getNextId(userId);
            current.setUserId(userId);
            current.setId(id);
            upload.setUploadTask(reference.child(current.getId()).putFile(current.getUri()));
        }
        return current != null;
    }

    public List<Image> getImages() {
        return images;
    }

    public LiveData<TransferInfo> getUploadInfo() {
        return uploadInfo;
    }

    public List<String> getUrls() {
        ArrayList<String> names = new ArrayList<>();
        for (Image img : images) {
            names.add(img.getUrl());
        }
        return names;
    }

    private Image getNextItem() {
        for (Image image : images) {
            if (!image.isUploaded())
                return image;
        }
        return null;
    }


    private int getCompleteCount() {
        int i = 0;
        for (Image image : images) {
            if (image.isUploaded())
                i++;
        }
        return i;
    }

}
