package com.minipoly.android.param_managers;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.entity.Realestate;
import com.minipoly.android.entity.RealestateInfo;

public class RealestateManager {
    public MutableLiveData<RealestateInfo> info = new MutableLiveData<>();
    private MutableLiveData<Realestate> realestate = new MutableLiveData<>();
    public MutableLiveData<String> area = new MutableLiveData<>();
    public String[] olds = new String[]{"0 - 2", "3 - 5", "6 - 10", "11 -20 ", "21 -50 ", " more"};
    public MutableLiveData<Integer> oldIndex = new MutableLiveData<>(0);

    public RealestateManager(Realestate realestate) {
        this.realestate.setValue(realestate);
        info.setValue(new RealestateInfo());
    }

    public void changeRooms(int i) {
        RealestateInfo r = info.getValue();
        r.setRoomCount(r.getRoomCount() + i);
        info.postValue(r);
    }


    public void changeFurnished(boolean furnished) {
        RealestateInfo r = info.getValue();
        r.setFurnished(furnished);
        info.postValue(r);
    }

    public void changeMonthlyRent(boolean b) {
        RealestateInfo r = info.getValue();
        r.setMonthlyRent(b);
        info.postValue(r);
    }

    public void changeYearlyRent(boolean b) {
        RealestateInfo r = info.getValue();
        r.setYearlyRent(b);
        info.postValue(r);
    }

    public void changebath(int i) {
        RealestateInfo r = info.getValue();
        r.setBathroomCount(r.getBathroomCount() + i);
        info.postValue(r);
    }

    public RealestateInfo getInfo() {
        return info.getValue();
    }

    public MutableLiveData<Realestate> getRealestate() {
        return realestate;
    }

    public void changeOld(int i) {
        Realestate r = realestate.getValue();
        r.setOld(r.getOld() + i);
        realestate.postValue(r);
    }

}
