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

    public MutableLiveData<Realestate> getRealestate() {
        return realestate;
    }

    public void changeOld(int i) {
        Realestate r = realestate.getValue();
        r.setOld(r.getOld() + i);
        realestate.postValue(r);
    }

}
