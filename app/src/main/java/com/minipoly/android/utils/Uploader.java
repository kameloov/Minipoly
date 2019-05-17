package com.minipoly.android.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.minipoly.android.ProgressInfo;
import com.minipoly.android.StorageManager;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.TransferInfo;
import com.minipoly.android.livedata.FireLiveUpload;

import java.util.List;

public class Uploader {

    private List<Image> imageList;
    private LiveData<TransferInfo> info;
    private static FireLiveUpload upload;
    private static int completed = 0;

    public static LiveData<TransferInfo> upload(List<Image> imageList) {
        upload = new FireLiveUpload();
        upload.setUploadTask(StorageManager.getRoot().putFile(imageList.get(0).getUri()));
        return Transformations.map(upload, input -> {
            TransferInfo ti = new TransferInfo();
            switch (input.getStatus()) {
                case SUCCESS:
                    completed++;
                    if (completed == imageList.size())
                        ti.setStatus(ProgressInfo.SUCCESS);
                    else {
                        ti.setStatus(ProgressInfo.PROGRESS);
                        upload.setUploadTask(StorageManager.getRoot().putFile(imageList.get(completed).getUri()));
                    }
                    break;
                case FAIL:
                    upload.setUploadTask(StorageManager.getRoot().putFile(imageList.get(completed).getUri()));
                    ti.setStatus(ProgressInfo.PROGRESS);
                    break;
            }
            ti.setProgress(input.getProgress());
            ti.setText(completed + "/" + imageList.size());
            return ti;
        });
    }


}
