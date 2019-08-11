package com.minipoly.android.param_managers;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.RealestateInfo;

import java.util.ArrayList;
import java.util.List;

public class RealestateManager {
    public MutableLiveData<RealestateInfo> info = new MutableLiveData<>(new RealestateInfo());
    public MutableLiveData<String> area = new MutableLiveData<>();
    public String[] olds = new String[]{"0 - 2", "3 - 5", "6 - 10", "11 -20 ", "21 -50 ", " more"};
    public MutableLiveData<Integer> oldIndex = new MutableLiveData<>(0);
    public MutableLiveData<CustomRadio> typeRadio = new MutableLiveData<>(new CustomRadio(false, "For Rent", "For Sale"));

    public void changeRooms(int i) {
        RealestateInfo r = info.getValue();
        r.setRoomCount(r.getRoomCount() + i);
        info.setValue(r);
        Log.e("changeRooms: ", "room count is " + r.getRoomCount());
    }

    public void changeFurnished(boolean furnished) {
        RealestateInfo r = info.getValue();
        r.setFurnished(furnished);
        info.setValue(r);
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
        RealestateInfo ri = info.getValue();
        ri.setRent(typeRadio.getValue().isChecked());
        return ri;
    }

    public static List<String> getTags(RealestateInfo r) {
        ArrayList<String> list = new ArrayList<>();
        if (r != null) {
            list.add(r.isFurnished() ? "Furnished" : "Empty");
            if (r.getRoomCount() > 0)
                list.add(r.getRoomCount() + " Rooms");
            if (r.getBathroomCount() > 0)
                list.add(r.getBathroomCount() + " Bathrooms");
            if (r.getArea() > 0)
                list.add(r.getArea() + " M");
            if (r.isYearlyRent())
                list.add("Annual rent");
            if (r.isMonthlyRent())
                list.add("Monthly rent");
            if (r.getOld() > 0)
                list.add(r.getOld() + " Year");
        }
        if (list.size() < 7) {
            for (int i = 0; i <= 7 - list.size(); i++)
                list.add(null);
        }
        return list;
    }

}
