package com.minipoly.android.ui.newadvrt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Realestate;
import com.minipoly.android.repository.RealestateRepository;

public class AddAdvrtDialogViewModel extends ViewModel {

    private MutableLiveData<CustomRadio> radio = new MutableLiveData<>();
    private MutableLiveData<Realestate> realestate = new MutableLiveData<>();

    public LiveData<Realestate> getRealestate() {
        return realestate;
    }

    public void changeRooms(int i) {
        Realestate r = realestate.getValue();
        r.setRoomCount(r.getRoomCount() + i);
        realestate.postValue(r);
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
        RealestateRepository.addRealestate(realestate);
    }
}
