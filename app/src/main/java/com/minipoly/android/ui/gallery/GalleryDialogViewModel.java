package com.minipoly.android.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.TransferInfo;
import com.minipoly.android.repository.UserRepository;
import com.minipoly.android.utils.ImageBuffer;
import com.minipoly.android.utils.Uploader;

import java.util.List;

public class GalleryDialogViewModel extends ViewModel {
    private MediatorLiveData<TransferInfo> info = new MediatorLiveData<>();
    public MutableLiveData<VMCommand> command = new MutableLiveData<>(VMCommand.IDLE);
    private LiveData<List<Image>> images = ImageBuffer.getImages();


    public LiveData<TransferInfo> getInfo() {
        return info;
    }

    public void addImage() {
        command.setValue(VMCommand.ADD_IMAGE);
    }

    public LiveData<List<Image>> getImages() {
        return images;
    }

    public void Upload() {
        Uploader uploader = new Uploader(UserRepository.getUserId());
        List<Image> imgLst = ImageBuffer.getImages().getValue();
        info.addSource(uploader.upload(imgLst), transferInfo -> info.setValue(transferInfo));
    }

    public enum VMCommand {ADD_IMAGE, IDLE}
}
