package com.minipoly.android.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.TransferInfo;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.ImageBuffer;
import com.minipoly.android.utils.Uploader;

import java.util.List;

public class GalleryDialogViewModel extends ViewModel {
    private MediatorLiveData<TransferInfo> info = new MediatorLiveData<>();
    private LiveData<List<Image>> images;

    public GalleryDialogViewModel() {
        images = ImageBuffer.getImages();
        if (images.getValue().size() == 0)
            ImageBuffer.addImage(new Image());
    }
    public LiveData<TransferInfo> getInfo() {
        return info;
    }


    public LiveData<List<Image>> getImages() {
        return images;
    }

    public void Upload() {
        Uploader uploader = new Uploader(UserRepository.getUserId());
        List<Image> imgLst = images.getValue();
        imgLst.remove(0);
        info.addSource(uploader.upload(imgLst), transferInfo -> info.setValue(transferInfo));
    }
}
