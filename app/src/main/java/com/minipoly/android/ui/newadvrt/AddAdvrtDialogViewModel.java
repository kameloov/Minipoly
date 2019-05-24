package com.minipoly.android.ui.newadvrt;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.UserManager;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Image;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.TransferInfo;
import com.minipoly.android.entity.UserBrief;
import com.minipoly.android.repository.RealestateRepository;
import com.minipoly.android.repository.SocialRepository;
import com.minipoly.android.utils.Uploader;

import java.util.List;

public class AddAdvrtDialogViewModel extends ViewModel {
    private MutableLiveData<CustomRadio> radio = new MutableLiveData<>();
    private MutableLiveData<Realestate> realestate = new MutableLiveData<>();
    private List<Image> images;
    private MediatorLiveData<TransferInfo> info = new MediatorLiveData<>();
    private LiveData<Image> defaultImage;
    private static Uploader uploader;
    public MutableLiveData<String> price = new MutableLiveData<>();
    public MutableLiveData<String> area = new MutableLiveData<>();
    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    public AddAdvrtDialogViewModel() {
        uploader = new Uploader(UserManager.getUserID());
        defaultImage = uploader.getDefaultImage();
    }

    public static Uploader getUploader() {
        return uploader;
    }

    public LiveData<Realestate> getRealestate() {
        return realestate;
    }

    public void changeRooms(int i) {
        Realestate r = realestate.getValue();
        r.setRoomCount(r.getRoomCount() + i);
        realestate.postValue(r);
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<TransferInfo> getInfo() {
        return info;
    }

    public LiveData<Image> getDefaultImage() {
        return defaultImage;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void changeFurnished(boolean furnished) {
        Realestate r = realestate.getValue();
        r.setFurnished(furnished);
        realestate.postValue(r);
    }

    public void changeMonthlyRent(boolean b) {
        Realestate r = realestate.getValue();
        r.setMonthlyRent(b);
        realestate.postValue(r);
    }

    public void changeYearlyRent(boolean b) {
        Realestate r = realestate.getValue();
        r.setYearlyRent(b);
        realestate.postValue(r);
    }

    public void changebath(int i) {
        Realestate r = realestate.getValue();
        r.setBathroomCount(r.getBathroomCount() + i);
        realestate.postValue(r);
    }

    public void changeOld(int i) {
        Realestate r = realestate.getValue();
        r.setOld(r.getOld() + i);
        realestate.postValue(r);
    }

    public LiveData<CustomRadio> getRadio() {
        return radio;
    }

    public void setRadio(CustomRadio radio) {
        this.radio.setValue(radio);
    }

    public void setRealestate(Realestate realestate) {
        this.realestate.setValue( realestate);
    }

    public void addRealestate() {
        Realestate r = realestate.getValue();
        r.setArea(Float.parseFloat(area.getValue()));
        r.setPrice(Float.parseFloat(price.getValue()));
        UserBrief brief = new UserBrief();
        // todo make the user Brief dynamic
        brief.setId(SocialRepository.getUserId());
        brief.setName("Kamel");
        brief.setPicture("XOOhwofsSZbVMo8l7l7JP2vbFnx2");
        brief.setId("XOOhwofsSZbVMo8l7l7JP2vbFnx2");
        brief.setDeals(12);
        brief.setStars(4);
        r.setUserBrief(brief);
        r.setRent(radio.getValue().isChecked());
        r.setImages(uploader.getImages());
        RealestateRepository.addRealestate(r, (s) -> {
            success.setValue(s);
            Log.e("add", "addRealestate: " + s);
        });
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isDataMissing() {
        Realestate r = realestate.getValue();

        return isEmpty(price.getValue()) || isEmpty(area.getValue()) || isEmpty(r.getText()) || isEmpty(r.getTitle());
    }
}
